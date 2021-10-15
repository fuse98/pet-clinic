package org.springframework.samples.petclinic.owner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class OwnerTheoryTest {
	public static Owner owner;

	@Before
	public void setup(){
		owner = new Owner();
	}

	@DataPoints
	public static String[] petNames = new String[]{
		"pet1", "pet2", "pet3"
	};

	@Theory
	public void testGetPets(Integer id, String name){
		Pet pet = new Pet();
		pet.setName(name);
		owner.addPet(pet);
		Assert.assertEquals(pet, owner.getPet(name));
		Assert.assertEquals(name, owner.getPet(name).getName());

	}
}
