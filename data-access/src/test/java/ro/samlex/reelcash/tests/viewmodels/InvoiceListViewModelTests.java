package ro.samlex.reelcash.tests.viewmodels;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import ro.samlex.reelcash.viewmodels.InvoiceListViewModel;
import ro.samlex.reelcash.viewmodels.SelectorViewModel;

public class InvoiceListViewModelTests {
    @Test
    public void givenNewInstance_inAnyCase_itIsASelectorViewModel() {
        assertThat(new InvoiceListViewModel(), instanceOf(SelectorViewModel.class));
    }
}
