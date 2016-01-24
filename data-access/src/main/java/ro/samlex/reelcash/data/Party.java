package ro.samlex.reelcash.data;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public class Party implements Serializable {
    private String name;
    private StreetAddress address;
    private BankInformation bankingInformation;
    private LegalInformation legalInformation;

    public Party() {
        name = "";
    }

    public String getName() {
        return name;
    }
    
    public void setName(String value) {
        if(value == null)
            throw new NullPointerException();
        name = value;
    }

    public StreetAddress getAddress( ){
        return this.address;
    }
    
    public void setAddress(StreetAddress address) {
        this.address = address;
    }
    
    public BankInformation getBankingInformation() {
        return bankingInformation;
    }

    public void setBankingInformation(String bankName, String accountNumber) {
        this.bankingInformation = new BankInformation(bankName, accountNumber);
    }

    public LegalInformation getLegalInformation() {
        return legalInformation;
    }

    public void setLegalInformation(String fiscalId, String registrationNumber) {
        this.legalInformation = new LegalInformation(fiscalId, registrationNumber);
    }

    public void save(OutputStream os) throws java.io.IOException {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        try (OutputStreamWriter writer = new OutputStreamWriter(os)) {
            writer.write(json);
        }
    }
    
    public void load(InputStream is) throws java.io.IOException {
        Gson gson = new Gson();
        try (InputStreamReader reader = new InputStreamReader(is)) {
            Party p = gson.fromJson(reader, Party.class);
            this.name = p.name;
            this.address = p.address;
            this.bankingInformation = p.bankingInformation;
            this.legalInformation = p.legalInformation;            
        }
    }
}
