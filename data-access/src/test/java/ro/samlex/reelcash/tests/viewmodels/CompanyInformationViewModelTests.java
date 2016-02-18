package ro.samlex.reelcash.tests.viewmodels;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import ro.samlex.reelcash.data.Party;
import ro.samlex.reelcash.io.OutputSink;
import ro.samlex.reelcash.viewmodels.CompanyInformationViewModel;

public class CompanyInformationViewModelTests {

    private static final Party TEST_PARTY = new Party();

    @BeforeClass
    public static void createTestPartyFile() {
        TEST_PARTY.setName("test");
    }

    @Test
    public void givenViewModel_inInitialState_doesNotHaveAModel() {
        CompanyInformationViewModel sut = new CompanyInformationViewModel();

        Party company = sut.getModel();

        assertNull(company);
    }

    @Test
    public void givenViewModel_whenSavingDataUsingOutputSink_outputDataIsAsExpected() {
        CompanyInformationViewModel sut = new CompanyInformationViewModel();
        sut.setModel(TEST_PARTY);
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

        final String expectedOutput = new Gson().toJson(TEST_PARTY);
        assertEquals(expectedOutput, writtenValues.get(0));
    }

    @Test(expected = IllegalStateException.class)
    public void givenViewModel_whenSavingDataWithoutSettingAModel_throwsIllegalStateException() throws IOException {
        CompanyInformationViewModel sut = new CompanyInformationViewModel();

        sut.save(new OutputSink() {
            @Override
            public Writer newWriter() throws IOException {
                return new StringWriter();
            }
        });

    }
}
