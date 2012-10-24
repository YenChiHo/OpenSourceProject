import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class PolynomialTest {

	@Before
	public void initial()
	{
		Polynomial polynomial=new Polynomial();
		polynomial.polynomial(10);
	}
	@Test
	public void testf() {
		initial();
		assertNull(Polynomial.f(5));
	}

}
