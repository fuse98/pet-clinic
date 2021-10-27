package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.springframework.samples.petclinic.model.NamedEntity;


class OwnerTest {

	private Owner owner;

	@BeforeEach
	public void setup() {
		owner = new Owner();
		owner.setId(1);
		owner.setFirstName("Mamad Diyar");
		owner.setLastName("Diyar Mamadi");
		owner.setAddress("Behesht Abad");
		owner.setCity("Tehroon");
		owner.setTelephone("09189998877");
	}

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


	@Test
	public void testGetPet() {
		String petName = "pishoo";
		Pet pet = new Pet();
		setFieldValue(NamedEntity.class, "name", pet, petName);
		setFieldValue(owner.getClass(), "pets", owner, new HashSet<>(Collections.singletonList(pet)));

		Pet foundPet = owner.getPet(petName);
		assertNotNull(foundPet);
		assertEquals(foundPet.getName(), petName);
	}

	@Test
	public void testGetPetNotFound() {
		String petName = "pishoo";
		String anotherPetName = "hapoo";
		Pet pet = new Pet();
		setFieldValue(NamedEntity.class, "name", pet, petName);
		setFieldValue(owner.getClass(), "pets", owner, new HashSet<>(Collections.singletonList(pet)));

		Pet foundPet = owner.getPet(anotherPetName);
		assertNull(foundPet);
	}

	@Test
	public void testGetPetBehavior() {
		String petName = "pishoo";
		Pet pet = mock(Pet.class);
		when(pet.isNew()).thenReturn(false);
		when(pet.getName()).thenReturn(petName);

		setFieldValue(owner.getClass(), "pets", owner, new HashSet<>(Collections.singletonList(pet)));

		Pet foundPet = owner.getPet(petName, true);
		assertNotNull(foundPet);
		assertEquals(foundPet.getName(), petName);

		verify(pet, times(2)).getName(); // if ignored, it shouldn't compare names
		verify(pet, times(1)).isNew();
	}

	@Test
	public void testGetPetIgnoreNewBehavior() {
		String petName = "pishoo";
		Pet pet = mock(Pet.class);
		when(pet.isNew()).thenReturn(true);
		when(pet.getName()).thenReturn(petName);

		setFieldValue(owner.getClass(), "pets", owner, new HashSet<>(Collections.singletonList(pet)));
		Pet foundPet = owner.getPet(petName, true);

		assertNull(foundPet);
		verify(pet, times(0)).getName(); // if ignored, it shouldn't compare names
		verify(pet, times(1)).isNew();

	}
}
