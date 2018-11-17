package model.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StorageManagerTest {

	private StorageManager storageManager = StorageManager.getInstance();
	
	@Test
	public void testSingleton() {
		StorageManager storageManager2 = StorageManager.getInstance();
		assertEquals(storageManager, storageManager2);
	}
}
