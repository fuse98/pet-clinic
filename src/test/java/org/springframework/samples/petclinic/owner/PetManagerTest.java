package org.springframework.samples.petclinic.owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.springframework.samples.petclinic.utility.PetTimedCache;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

//import static org.junit.jupiter.api.Assertions.*;

class PetManagerTest {

	private static final int TEST_OWNER_ID = 1;
	private static final int TEST_PET_ID = 1;
	private static final String TEST_PET_NAME = "BADOOM";

	private PetManager petManager;
	private OwnerRepository mockOwnerRepo;
	private PetTimedCache mockPets;
	private Logger logger;
	private Owner mOwner;
	private Owner owner;
	private Pet pet;
	private Pet mPet;
	private PetType petType;

//	logger is a dummy double, pets and ownerRepo can be used as mocks or stubs
	@BeforeEach
	void setup(){
//		mock or stub
		mockOwnerRepo = mock(OwnerRepository.class);
//		mock or stub
		mockPets = mock(PetTimedCache.class);

		pet = new Pet();
		pet.setName(TEST_PET_NAME);
		petType = new PetType();
		pet.setType(petType);
//		mock
		mPet = mock(Pet.class);

		owner = new Owner();
		owner.setId(TEST_OWNER_ID);
		owner.addPet(pet);
//		mock
		mOwner = mock(Owner.class);

		when(mockOwnerRepo.findById(TEST_OWNER_ID)).thenReturn(owner);
		when(mockPets.get(TEST_PET_ID)).thenReturn(mPet);
//		dummy
		logger = mock(Logger.class);
//		SUT
		petManager = new PetManager(mockPets, mockOwnerRepo, logger);
	}

//	stub
//	state
//  mockist
	@Test
	public void testFindOwner(){
		Owner foundOwner = this.petManager.findOwner(TEST_OWNER_ID);
		assertEquals(this.owner, foundOwner);
	}

//	mock
//	behavior
//	mockist
	@Test
	public void testNewPet(){
		Pet pet = this.petManager.newPet(this.mOwner);
		verify(this.mOwner).addPet(pet);
	}


//	mock
// behavior
//	mockist
	@Test
	public void testFindPet(){
		Pet pet = this.petManager.findPet(TEST_PET_ID);
		verify(this.mockPets).get(TEST_PET_ID);
	}

//	mock
// behavior
//	mockist
	@Test
	public void testSavePet(){
		this.petManager.savePet(this.pet, this.mOwner);
		verify(this.mOwner).addPet(this.pet);
		verify(this.mockPets).save(this.pet);
	}

//	stub
// state
//	classic
	@Test
	public void testGetOwnerPets(){
		List<Pet> pets = this.petManager.getOwnerPets(TEST_OWNER_ID);
		assertEquals(this.pet, pets.get(0));
	}

//	stub
// state
//	classic
	@Test
	public void testGetOwnerPetTypes(){
		Set<PetType> petTypes = this.petManager.getOwnerPetTypes(TEST_OWNER_ID);
		assertTrue(petTypes.contains(petType));
	}

//	mock
// behavior
//	mockist
	@Test
	public void testGetVisitsBetween(){
		LocalDate d1 = LocalDate.of(2020, 10, 10);
		LocalDate d2 = LocalDate.of(2020, 12, 10);
		this.petManager.getVisitsBetween(TEST_PET_ID, d1, d2);
		verify(mockPets).get(TEST_PET_ID);
		verify(mPet).getVisitsBetween(d1, d2);
	}
}
