package org.springframework.samples.petclinic.utility;

import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;


public class PriceCalculatorTest {
	private static int INFANT_YEARS = 2;
	private static double RARE_INFANCY_COEF = 1.4;
	private static double BASE_RARE_COEF = 1.2;
	private static int DISCOUNT_MIN_SCORE = 10;
	private static int DISCOUNT_PRE_VISIT = 2;


	private PriceCalculator priceCalculator = new PriceCalculator();
	Pet notInfantPet, infantPet, exactInfantPet;
	Visit lessThan100Days, exactly100Days, moreThan100Days;
	ArrayList<Visit> emptyVisits, lastVisitLessThan100, lastVisitMoreThen100, lastVisitExactly100;
	ArrayList<Pet> moreThanMinScorePets, exactMinScorePets, lessThanMinScorePets;
	Double baseCharge = 1000.0;
	Double basePricePerPet = 200.0;


	@Before
	public void setup() {
		LocalDate oldBirthDate = LocalDate.from(ZonedDateTime.now().minusYears(5));
		LocalDate infantBirthDate = LocalDate.from(ZonedDateTime.now().minusMonths(2));
		LocalDate exactInfantBirthDate = LocalDate.from(ZonedDateTime.now().minusYears(2));


		notInfantPet = mock(Pet.class);
		when(notInfantPet.getBirthDate()).thenReturn(oldBirthDate);

		infantPet = mock(Pet.class);
		when(infantPet.getBirthDate()).thenReturn(infantBirthDate);

		exactInfantPet = mock(Pet.class);
		when(exactInfantPet.getBirthDate()).thenReturn(exactInfantBirthDate);


		lessThan100Days = mock(Visit.class);
		when(lessThan100Days.getDate()).thenReturn(LocalDate.from(ZonedDateTime.now().minusDays(20)));

		exactly100Days = mock(Visit.class);
		when(exactly100Days.getDate()).thenReturn(LocalDate.from(ZonedDateTime.now().minusDays(100)));

		moreThan100Days = mock(Visit.class);
		when(moreThan100Days.getDate()).thenReturn(LocalDate.from(ZonedDateTime.now().minusDays(120)));


		emptyVisits = new ArrayList<>();

		lastVisitExactly100 = new ArrayList<>();
		lastVisitExactly100.add(exactly100Days);

		lastVisitLessThan100 = new ArrayList<>();
		lastVisitLessThan100.add(lessThan100Days);

		lastVisitMoreThen100 = new ArrayList<>();
		lastVisitMoreThen100.add(moreThan100Days);


	}

	@Test
	public void when_isNotInfantAndEmptyVisitsAndLessThanMinScore_expect_() {
		when(notInfantPet.getVisitsUntilAge(anyInt())).thenReturn(emptyVisits);
		lessThanMinScorePets = new ArrayList<>();
		lessThanMinScorePets.add(notInfantPet);
		double price = PriceCalculator.calcPrice(lessThanMinScorePets, baseCharge, basePricePerPet);
		assertEquals(240, price, 0.01);
	}


	@Test
	public void when_isNotInfantAndEmptyVisitsThan100AndExactlyMinScore() {
		when(notInfantPet.getVisitsUntilAge(anyInt())).thenReturn(emptyVisits);
		exactMinScorePets = new ArrayList<>();

		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);

		double price = PriceCalculator.calcPrice(exactMinScorePets, baseCharge, basePricePerPet);
		assertEquals(5560, price, 0.01);
	}


	@Test
	public void when_isNotInfantAndLastVisitLessThan100AndExactlyMinScore() {
		when(notInfantPet.getVisitsUntilAge(anyInt())).thenReturn(lastVisitLessThan100);
		exactMinScorePets = new ArrayList<>();

		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);

		double price = PriceCalculator.calcPrice(exactMinScorePets, baseCharge, basePricePerPet);
		assertEquals(5560, price, 0.01);
	}

	@Test
	public void when_isNotInfantAndLastVisitMoreThen100AndExactlyMinScore() {
		when(notInfantPet.getVisitsUntilAge(anyInt())).thenReturn(lastVisitMoreThen100);
		exactMinScorePets = new ArrayList<>();

		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);

		double price = PriceCalculator.calcPrice(exactMinScorePets, baseCharge, basePricePerPet);
		assertEquals(6560, price, 0.01);
	}

	@Test
	public void when_isNotInfantAndLastVisitExactly100AndExactlyMinScore() {
		when(notInfantPet.getVisitsUntilAge(anyInt())).thenReturn(lastVisitExactly100);
		exactMinScorePets = new ArrayList<>();

		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);

		double price = PriceCalculator.calcPrice(exactMinScorePets, baseCharge, basePricePerPet);
		assertEquals(6560, price, 0.01);
	}

// NEW TEST

	@Test
	public void when_isNotInfantAndEmptyVisitsThan100AndMoreThanMinScore() {
		when(notInfantPet.getVisitsUntilAge(anyInt())).thenReturn(emptyVisits);
		exactMinScorePets = new ArrayList<>();

		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);

		double price = PriceCalculator.calcPrice(exactMinScorePets, baseCharge, basePricePerPet);
		assertEquals(12360, price, 0.01);
	}


	@Test
	public void when_isNotInfantAndLastVisitLessThan100AndMoreThanMinScore() {
		when(notInfantPet.getVisitsUntilAge(anyInt())).thenReturn(lastVisitLessThan100);
		exactMinScorePets = new ArrayList<>();

		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);

		double price = PriceCalculator.calcPrice(exactMinScorePets, baseCharge, basePricePerPet);
		assertEquals(12360, price, 0.01);
	}

	@Test
	public void when_isNotInfantAndLastVisitMoreThen100AndMoreThanMinScore() {
		when(notInfantPet.getVisitsUntilAge(anyInt())).thenReturn(lastVisitMoreThen100);
		exactMinScorePets = new ArrayList<>();

		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);

		double price = PriceCalculator.calcPrice(exactMinScorePets, baseCharge, basePricePerPet);
		assertEquals(15360, price, 0.01);
	}

	@Test
	public void when_isNotInfantAndLastVisitExactly100AndMoreThanMinScore() {
		when(notInfantPet.getVisitsUntilAge(anyInt())).thenReturn(lastVisitExactly100);
		exactMinScorePets = new ArrayList<>();

		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);
		exactMinScorePets.add(notInfantPet);

		double price = PriceCalculator.calcPrice(exactMinScorePets, baseCharge, basePricePerPet);
		assertEquals(15360, price, 0.01);
	}

// NEW


	@Test
	public void when_isInfantAndEmptyVisitsAndLessThanMinScore() {
		when(infantPet.getVisitsUntilAge(anyInt())).thenReturn(emptyVisits);
		lessThanMinScorePets = new ArrayList<>();
		lessThanMinScorePets.add(infantPet);
		double price = PriceCalculator.calcPrice(lessThanMinScorePets, baseCharge, basePricePerPet);
		assertEquals(336, price, 0.01);
	}


	@Test
	public void when_isInfantAndEmptyVisitsAndExactlyMinScore() {
		when(infantPet.getVisitsUntilAge(anyInt())).thenReturn(emptyVisits);
		exactMinScorePets = new ArrayList<>();

		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);

		double price = PriceCalculator.calcPrice(exactMinScorePets, baseCharge, basePricePerPet);
		assertEquals(170184, price, 0.01);
	}


	@Test
	public void when_isInfantAndLastVisitLessThan100AndExactlyMinScore() {
		when(infantPet.getVisitsUntilAge(anyInt())).thenReturn(lastVisitLessThan100);
		exactMinScorePets = new ArrayList<>();

		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);

		double price = PriceCalculator.calcPrice(exactMinScorePets, baseCharge, basePricePerPet);
		assertEquals(170184, price, 0.01);
	}

	@Test
	public void when_isInfantAndLastVisitMoreThen100AndExactlyMinScore() {
		when(infantPet.getVisitsUntilAge(anyInt())).thenReturn(lastVisitMoreThen100);
		exactMinScorePets = new ArrayList<>();

		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);

		double price = PriceCalculator.calcPrice(exactMinScorePets, baseCharge, basePricePerPet);
		assertEquals(233184, price, 0.01);
	}

	@Test
	public void when_isInfantAndLastVisitExactly100AndExactlyMinScore() {
		when(infantPet.getVisitsUntilAge(anyInt())).thenReturn(lastVisitExactly100);
		exactMinScorePets = new ArrayList<>();

		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);
		exactMinScorePets.add(infantPet);

		double price = PriceCalculator.calcPrice(exactMinScorePets, baseCharge, basePricePerPet);
		assertEquals(233184, price, 0.01);
	}


	@Test
	public void when_isExactInfantAndEmptyVisitsAndLessThanMinScore() {
		when(exactInfantPet.getVisitsUntilAge(anyInt())).thenReturn(emptyVisits);
		lessThanMinScorePets = new ArrayList<>();
		lessThanMinScorePets.add(exactInfantPet);
		double price = PriceCalculator.calcPrice(lessThanMinScorePets, baseCharge, basePricePerPet);
		assertEquals(336, price, 0.01);
	}


	@Test
	public void when_isExactInfantAndEmptyVisitsAndExactlyMinScore() {
		when(exactInfantPet.getVisitsUntilAge(anyInt())).thenReturn(emptyVisits);
		exactMinScorePets = new ArrayList<>();

		exactMinScorePets.add(exactInfantPet);
		exactMinScorePets.add(exactInfantPet);
		exactMinScorePets.add(exactInfantPet);
		exactMinScorePets.add(exactInfantPet);
		exactMinScorePets.add(exactInfantPet);
		exactMinScorePets.add(exactInfantPet);
		exactMinScorePets.add(exactInfantPet);
		exactMinScorePets.add(exactInfantPet);
		exactMinScorePets.add(exactInfantPet);
		exactMinScorePets.add(exactInfantPet);

		double price = PriceCalculator.calcPrice(exactMinScorePets, baseCharge, basePricePerPet);
		assertEquals(170184, price, 0.01);
	}


}
