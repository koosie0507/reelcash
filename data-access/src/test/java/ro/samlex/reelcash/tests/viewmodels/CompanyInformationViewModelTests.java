package ro.samlex.reelcash.tests.viewmodels;

import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import ro.samlex.reelcash.data.Party;
import ro.samlex.reelcash.data.StreetAddress;
import ro.samlex.reelcash.io.FileInputSource;
import ro.samlex.reelcash.io.OutputSink;
import ro.samlex.reelcash.viewmodels.CompanyInformationViewModel;

public class CompanyInformationViewModelTests {

    private static final Party testParty = new Party();

    @BeforeClass
    public static void createTestPartyFile() {
        testParty.setName("test");
        testParty.setAddress(new StreetAddress("test street", "test town", "test region", "test postal code", "test country"));
        testParty.setBankingInformation("test bank", "test account");
        testParty.setLegalInformation("test fiscal ID", "test registration number");
    }

    @Test
    public void givenViewModel_inInitialState_hostsAnInitializedModel() {
        CompanyInformationViewModel sut = new CompanyInformationViewModel();

        Party company = sut.getModel();

        assertNotNull(company);
    }

    @Test
    public void givenViewModel_whenSavingDataUsingOutputSink_outputDataIsAsExpected() {
        CompanyInformationViewModel sut = new CompanyInformationViewModel(testParty);
        final List<String> writtenValues = new ArrayList<>();

        try {
            sut.save(new OutputSink() {
                @Override
                public Writer newWriter() throws IOException {
                    return new Writer() {
                        @Override
                        public void write(char[] chars, int i, int i1) throws IOException {
                            writtenValues.add(new String(chars, i, i1));
                        }

                        @Override
                        public void flush() throws IOException {
                        }

                        @Override
                        public void close() throws IOException {
                        }
                    };
                }
            });
        } catch (IOException ex) {
            fail("Unable to save company information: " + ex.getMessage());
        }

        final String expectedOutput = new Gson().toJson(testParty);
        assertEquals(expectedOutput, writtenValues.get(0));
    }

    private static class StringOutputSink implements OutputSink {

        private String value;

        public Writer newWriter() throws IOException {
            return new Writer() {
                @Override
                public void write(char[] chars, int i, int i1) throws IOException {
                    value = new String(chars);
                }

                @Override
                public void flush() throws IOException {
                }

                @Override
                public void close() throws IOException {
                }

            };
        }

        public String getString() {
            return value;
        }
    }
}
