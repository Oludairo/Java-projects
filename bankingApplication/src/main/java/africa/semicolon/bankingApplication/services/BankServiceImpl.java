package africa.semicolon.bankingApplication.services;

import africa.semicolon.bankingApplication.data.models.Account;
import africa.semicolon.bankingApplication.data.models.Bank;
import africa.semicolon.bankingApplication.data.models.Customer;
import africa.semicolon.bankingApplication.data.repositories.BankRepository;
import africa.semicolon.bankingApplication.data.repositories.BankRepositoryImpl;
import africa.semicolon.bankingApplication.dtos.requests.CreateAccountRequest;

import java.util.List;

public class BankServiceImpl implements BankService{
//    private static  int lastBankIdCreated = 0;
    private final BankRepository bankRepository = new BankRepositoryImpl();
    private final CustomerService customerService = new CustomerServiceImpl();
    @Override
    public String createBank(String bankName) {
        String bankId = generateBankId();
        Bank bank = new Bank(bankId);
        bank.setName(bankName);
        Bank savedBank = bankRepository.save(bank);
        return savedBank.getId();
    }
    private String generateBankId(){
        int lastBankIdCreated = findAllBanks().size();
        return String.format("%02d", ++lastBankIdCreated);
    }


    @Override
    public List<Bank> findAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public String createAccount(CreateAccountRequest createAccountRequest) {
        String bankId = createAccountRequest.getBankId();
        String accountNumber = generateSuffixFor(bankId);
        Customer customer = new Customer();

        Account account = new Account();
        account.setNumber(bankId + accountNumber);
        account.setType(createAccountRequest.getAccountType());
        customer.setFirstName(createAccountRequest.getFirstName());
        customer.setLastName(createAccountRequest.getLastName());
        customer.getAccounts().add(account);

        Bank bank = bankRepository.findByBankId(bankId);
        bank.getAccounts().add(account);
        bankRepository.save(bank);
        return account.getNumber();
    }

    private String generateSuffixFor(String bankId){
        Bank bank = bankRepository.findByBankId(bankId);
        int lastNumber = bank.getAccounts().size();
        return String.format("%08d", ++lastNumber);
    }


}
