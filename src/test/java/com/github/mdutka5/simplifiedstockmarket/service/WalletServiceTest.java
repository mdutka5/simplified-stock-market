package com.github.mdutka5.simplifiedstockmarket.service;

import com.github.mdutka5.simplifiedstockmarket.exception.BadRequestException;
import com.github.mdutka5.simplifiedstockmarket.exception.NotFoundException;
import com.github.mdutka5.simplifiedstockmarket.model.BankStock;
import com.github.mdutka5.simplifiedstockmarket.model.Wallet;
import com.github.mdutka5.simplifiedstockmarket.model.WalletStock;
import com.github.mdutka5.simplifiedstockmarket.repository.WalletRepository;
import com.github.mdutka5.simplifiedstockmarket.repository.WalletStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;
    @Mock
    private WalletStockRepository walletStockRepository;
    @Mock
    private BankStockService bankStockService;
    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private WalletService walletService;

    private BankStock aaplStock;
    private WalletStock walletStock;
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        aaplStock = new BankStock("AAPL", 100);
        walletStock = new WalletStock(1L, "wallet1", "AAPL", 5);
        wallet = new Wallet("wallet1");
    }

    @Test
    void shouldThrowNotFoundWhenStockDoesNotExist() {
        when(bankStockService.findByStockName("AAPL"))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                walletService.trade("wallet1", "AAPL", "buy")
        );
    }

    @Test
    void shouldThrowBadRequestWhenBankQuantityIsZero() {
        aaplStock.setStockQuantity(0);
        when(bankStockService.findByStockName("AAPL"))
                .thenReturn(Optional.of(aaplStock));

        assertThrows(BadRequestException.class, () ->
                walletService.trade("wallet1", "AAPL", "buy")
        );
    }

    @Test
    void shouldThrowNotFoundWhenWalletDoesNotExist() {
        when(walletRepository.findById("wallet1"))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                walletService.getWallet("wallet1"));
    }

    @Test
    void shouldThrowBadRequestWhenSellingZeroQuantityStock() {
        WalletStock zeroStock = new WalletStock(1L, "wallet1", "AAPL", 0);

        when(bankStockService.findByStockName("AAPL"))
                .thenReturn(Optional.of(aaplStock));
        when(walletRepository.findById("wallet1"))
                .thenReturn(Optional.of(wallet));
        when(walletStockRepository.findByWalletIdAndStockName("wallet1", "AAPL"))
                .thenReturn(Optional.of(zeroStock));

        assertThrows(BadRequestException.class, () ->
                walletService.trade("wallet1", "AAPL", "sell")
        );
    }

    @Test
    void shouldThrowNotFoundWhenSellingStockNotInWallet() {
        when(bankStockService.findByStockName("AAPL"))
                .thenReturn(Optional.of(aaplStock));
        when(walletRepository.findById("wallet1"))
                .thenReturn(Optional.of(wallet));
        when(walletStockRepository.findByWalletIdAndStockName("wallet1", "AAPL"))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                walletService.trade("wallet1", "AAPL", "sell")
        );
    }

    @Test
    void shouldLogActivityOnSuccessfulBuy() {
        when(bankStockService.findByStockName("AAPL"))
                .thenReturn(Optional.of(aaplStock));
        when(walletRepository.findById("wallet1"))
                .thenReturn(Optional.of(wallet));
        when(walletStockRepository.findByWalletIdAndStockName("wallet1", "AAPL"))
                .thenReturn(Optional.of(walletStock));

        walletService.trade("wallet1", "AAPL", "buy");

        verify(auditLogService).logActivity("buy", "wallet1", "AAPL");
    }
}