package org.springframework.samples.petclinic.owner;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.NamedEntity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class OwnerTest {


	private Field getAccessibleField(Class _class, String fieldName) {
		try {
			Field field = _class.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field;
		} catch (NoSuchFieldException e) {
			return null;
		}
	}

	private void setFieldValue(Class _class, String fieldName, Object obj, Object value) {
		Field field = getAccessibleField(_class, fieldName);
		try {
			assert field != null;
			field.set(obj, value);
		} catch (IllegalAccessException ignored) {}
	}


	@BeforeEach
	public void setup() {
		Pet p1 = new Pet();
		setFieldValue(p1.getClass(), "name", p1, "test_pet_1");

		Pet p2 = new Pet();
		setFieldValue(p2.getClass(), "name", p2, "test_pet_2");
	}
	@Test
	public void testSetter_setsProperly() throws IllegalAccessException {
		final Owner owner = new Owner();
		owner.setAddress("foo");
		Field field = getAccessibleField(owner.getClass(), "address");
		assertEquals("Fields didn't match", field.get(owner), "foo");
	}

	@Test
	public void testGetter_getsValue() {
		final Owner owner = new Owner();
		setFieldValue(owner.getClass(), "address", owner, "bar");
		final String result = owner.getAddress();
		assertEquals("field wasn't retrieved properly", result, "bar");
	}

	@Test
	public void testGetPetsInternalEmpty(){
		final Owner owner = new Owner();
		final Set<Pet> petsInternal = owner.getPetsInternal();
		assertEquals("pets internal wasn't retrieved properly", petsInternal, new HashSet<>());
	}


	@Test
	public void testAddPet() throws NoSuchFieldException, IllegalAccessException {
		final Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setName("Test Pet");
		owner.addPet(pet);

		final Field field = getAccessibleField(owner.getClass(), "pets");

		assertEquals("pets internal wasn't retrieved properly",
			field.get(owner), new HashSet<>(Arrays.asList(pet)));
	}

	@Test
	public void testGetPetsInternalNotEmpty(){
		final Owner owner = new Owner();
		Pet pet = new Pet();
		pet.setName("test pet");
		owner.addPet(pet);

		final Set<Pet> petsInternal = owner.getPetsInternal();
		assertEquals("pets internal wasn't retrieved properly", petsInternal, new HashSet<>(Arrays.asList(pet)));
	}

	@Test
	public void testGetPet(){
		Pet p1 = new Pet();
		setFieldValue(NamedEntity.class, "name", p1, "test_pet_1");

		Pet p2 = new Pet();
		setFieldValue(NamedEntity.class, "name", p2, "test_pet_2");

		final Owner owner = new Owner();
		setFieldValue(owner.getClass(), "pets", owner, new HashSet<>(Arrays.asList(p1, p2)));

		final Pet found_pet = owner.getPet("test_pet_1", false);
		assertEquals("pet wasn't retrieved properly", found_pet, p1);
	}

	@Test
	public void testGetPetNotFound(){
		Pet p1 = new Pet();
		setFieldValue(NamedEntity.class, "name", p1, "test_pet_1");

		final Owner owner = new Owner();
		setFieldValue(owner.getClass(), "pets", owner, new HashSet<>(Arrays.asList(p1)));

		final Pet found_pet = owner.getPet("blah_blah", false);
		assertEquals("pet wasn't retrieved properly", found_pet, null);
	}

	@Test
	public void testGetPetIgnoreNew(){
		Pet p1 = new Pet();
		setFieldValue(NamedEntity.class, "name", p1, "test_pet_1");

		final Owner owner = new Owner();
		setFieldValue(owner.getClass(), "pets", owner, new HashSet<>(Arrays.asList(p1)));

		final Pet found_pet = owner.getPet("test_pet_1", true);
		assertEquals("pet wasn't retrieved properly", found_pet, null);
	}

	@Test
	public void testGetPets(){
		Pet p1 = new Pet();
		setFieldValue(NamedEntity.class, "name", p1, "test_pet_2");

		Pet p2 = new Pet();
		setFieldValue(NamedEntity.class, "name", p2, "test_pet_1");

		final Owner owner = new Owner();
		setFieldValue(owner.getClass(), "pets", owner, new HashSet<>(Arrays.asList(p1, p2)));

		final List<Pet> pets = owner.getPets();
		assertEquals("Wrong order pet 1", p1, pets.get(1));
		assertEquals("Wrong order pet 0", p2, pets.get(0));
	}
}
