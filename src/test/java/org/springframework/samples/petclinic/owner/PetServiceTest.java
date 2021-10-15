package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.utility.PetTimedCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class PetServiceTest {
	public Integer petId;
	public Pet expectedPet;
	private static PetTimedCache mockCache = mock(PetTimedCache.class);;
	private static List<Pet> pets = Arrays.asList(new Pet(), new Pet(), new Pet());
	PetService petService;

	public PetServiceTest(Integer petId, Pet expectedPet) {
		this.petId = petId;
		this.expectedPet = expectedPet;
		petService = new PetService(mockCache, null, LoggerFactory.getLogger(PetService.class));
	}

	@Parameterized.Parameters
	public static Collection<Object[]> parameters(){
		ArrayList<Object[]> params = new ArrayList<>();
		for(int i = 0; i < pets.size(); i ++) {
			pets.get(i).setId(i);
			params.add(new Object[]{i, pets.get(i)});
			when(mockCache.get(i)).thenReturn(pets.get(i));
		}
		when(mockCache.get(100)).thenReturn(null);
		params.add(new Object[]{100, null});
		return params;
	}


	@Test
	public void findPetTest(){
		Pet foundPet = petService.findPet(petId);
		Assertions.assertEquals(expectedPet, foundPet);
	}


}
