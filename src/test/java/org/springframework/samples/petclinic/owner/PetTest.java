package org.springframework.samples.petclinic.owner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.samples.petclinic.visit.Visit;

import java.nio.file.LinkOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(Theories.class)
public class PetTest {
	Pet pet;
	public PetTest(){}

	@Before
	public void setup(){
		pet = new Pet();
	}

	@After
	public void teardown(){
		pet = null;
	}

	@DataPoints
	public static LocalDate[] third_dates = new LocalDate[]{
		LocalDate.of(1300, 1, 1),
	};

	@DataPoints
	public static LocalDate[] second_dates = new LocalDate[]{
		LocalDate.of(1350, 1, 1),
		LocalDate.of(1388, 1, 1),
		LocalDate.of(1340, 1, 1),
		LocalDate.of(1398, 1, 1),
	};

	@DataPoints
	public static LocalDate[] first_dates = new LocalDate[]{
		LocalDate.of(1300, 6, 1),
		LocalDate.of(1300, 8, 1),
		LocalDate.of(1300, 4, 1),
		LocalDate.of(1300, 9, 1),
	};

	@DataPoints
	public static LocalDate[] forth_dates = new LocalDate[]{
		LocalDate.of(1300, 1, 5),
		LocalDate.of(1300, 1, 16),
		LocalDate.of(1300, 1, 13),
		LocalDate.of(1300, 1, 4),
	};

	@Theory
	public void testSortingInGetVisits(LocalDate d1, LocalDate d2, LocalDate d3, LocalDate d4){
		Visit v1 = new Visit();
		Visit v2 = new Visit();
		Visit v3 = new Visit();
		Visit v4 = new Visit();
		ArrayList<LocalDate> inputs = new ArrayList<LocalDate>(){{
			add(d1); add(d2); add(d3); add(d4);
		}};
		v1.setDate(d1);
		v2.setDate(d2);
		v3.setDate(d3);
		v4.setDate(d4);

		ArrayList<Visit> visits = new ArrayList<Visit>(){{
			add(v1); add(v3); add(v2); add(v4);
		}};
		LocalDate min_date = LocalDate.of(1300, 1, 1);
		Assume.assumeTrue(inputs.contains(min_date));
		pet.setVisitsInternal(visits);
		List<Visit> result = pet.getVisits();
		Assert.assertEquals(1300, result.get(3).getDate().getYear());
		Assert.assertEquals(1, result.get(3).getDate().getDayOfMonth());
		Assert.assertEquals(1, result.get(3).getDate().getMonthValue());
		Assert.assertEquals(4, result.toArray().length);
	}

}
