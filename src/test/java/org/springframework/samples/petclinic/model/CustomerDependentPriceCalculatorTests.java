package org.springframework.samples.petclinic.model;

import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.model.priceCalculators.CustomerDependentPriceCalculator;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerDependentPriceCalculatorTests {
	private CustomerDependentPriceCalculator priceCalculator = new CustomerDependentPriceCalculator();
	Pet notInfantNotRarePet, infantNotRarePet, infantRarePet, notInfantRarePet;
	Double baseCharge = 1000.0;
	Double basePricePerPet = 200.0;
	@Before
	public void setup()
	{
		PetType normalPetType = mock(PetType.class);
		when(normalPetType.getRare()).thenReturn(false);
		PetType rarePetType = new PetType();

		Date oldBirthDate = Date.from(ZonedDateTime.now().minusYears(5).toInstant());
		Date infantBirthDate = Date.from(ZonedDateTime.now().minusMonths(2).toInstant());


		notInfantNotRarePet = mock(Pet.class);
		when(notInfantNotRarePet.getBirthDate()).thenReturn(oldBirthDate);
		when(notInfantNotRarePet.getType()).thenReturn(normalPetType);

		infantNotRarePet = mock(Pet.class);
		when(infantNotRarePet.getBirthDate()).thenReturn(infantBirthDate);
		when(infantNotRarePet.getType()).thenReturn(normalPetType);

		infantRarePet = mock(Pet.class);
		when(infantRarePet.getBirthDate()).thenReturn(infantBirthDate);
		when(infantRarePet.getType()).thenReturn(rarePetType);


		notInfantRarePet = mock(Pet.class);
		when(notInfantRarePet.getBirthDate()).thenReturn(oldBirthDate);
		when(notInfantRarePet.getType()).thenReturn(rarePetType);




	}

	@Test
	public void when_isNotRareAndIsNotInfantAndNotEnoughDiscountScoreAndNotGold_expect_NoBaseChargeNoCoefImpacted()
	{
		List<Pet> pets = new ArrayList<>();
		pets.add(notInfantNotRarePet);
		UserType userType = UserType.NEW;
		double price = priceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);
		assertEquals(200, price, 0.01);
	}

	@Test
	public void when_isRareAndIsNotInfantAndNotEnoughDiscountScoreAndGold_expect_BaseChargeAndBaseRareCoefImapcted()
	{
		List<Pet> pets = new ArrayList<>();
		pets.add(notInfantRarePet);
		UserType userType = UserType.GOLD;
		double price = priceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);
		assertEquals(1192.0, price, 0.01);
	}

	@Test
	public void when_allFourKindPetAndGold_expect_discountCountAndGoldDiscountImpacted()
	{
		List<Pet> pets = new ArrayList<>();
		pets.add(infantRarePet);
		pets.add(infantNotRarePet);
		pets.add(notInfantNotRarePet);
		pets.add(notInfantRarePet);
		pets.add(infantRarePet);
		pets.add(infantNotRarePet);
		pets.add(notInfantNotRarePet);
		pets.add(notInfantRarePet);

		UserType userType = UserType.GOLD;
		double price = priceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);
		assertEquals(2425.6, price, 0.01);
	}

	@Test
	public void when_allFourKindPetAndGold_expect_discountCountAndNewDiscountImpacted()
	{
		List<Pet> pets = new ArrayList<>();
		pets.add(infantRarePet);
		pets.add(infantNotRarePet);
		pets.add(notInfantNotRarePet);
		pets.add(notInfantRarePet);
		pets.add(infantRarePet);
		pets.add(infantNotRarePet);
		pets.add(notInfantNotRarePet);
		pets.add(notInfantRarePet);

		UserType userType = UserType.NEW;
		double price = priceCalculator.calcPrice(pets, baseCharge, basePricePerPet, userType);
		assertEquals(2930.4, price, 0.01);
	}


}
