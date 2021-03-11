package email.course.springboottestproject.lesson1;

import email.course.springboottestproject.lesson1.PricingService;
import email.course.springboottestproject.lesson1.ProductVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PricingServiceTest {

    @Mock
    private ProductVerifier mockedProductVerifier;



    @Test
    public void shouldReturnCheapPriceWhenProductIsInStockOfCompetitor() {
        when(mockedProductVerifier.isCurrentlyInStockOfCompetitor("AirPods"))
                .thenReturn(true); //Specify what boolean value to return

        PricingService cut = new PricingService(mockedProductVerifier);

        assertEquals(new BigDecimal("99.99"), cut.calculatePrice("AirPods"));
    }
}