package africa.semicolon.bankingApplication.data.repositories;

import africa.semicolon.bankingApplication.data.models.Bank;

import java.util.ArrayList;
import java.util.List;

public class BankRepositoryImpl implements BankRepository{
    List<Bank> banks = new ArrayList<>();

    @Override
    public Bank save(Bank bank) {
        Bank foundBank = findByBankId(bank.getId());
        if (foundBank != null){
            delete(foundBank);
        }
        banks.add(bank);
        return findByBankId(bank.getId());
    }

    @Override
    public Bank findByBankId(String id) {
        for (Bank bank: banks){
            if (bank.getId().equalsIgnoreCase(id))
            return bank;
        }
        return null;
    }

    @Override
    public void delete(Bank bank) {
        banks.remove(bank);

    }

    @Override
    public void delete(String bankId) {
        Bank bank = findByBankId(bankId);
        delete(bank);
    }

    @Override
    public List<Bank> findAll() {
        return banks;
    }
}
