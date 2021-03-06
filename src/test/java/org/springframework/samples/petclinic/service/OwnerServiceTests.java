/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided
 * by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning that we
 * don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * OwnerServiceTests#clinicService clinicService}</code> instance variable, which uses
 * autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in
 * its own transaction, which is automatically rolled back by default. Thus, even if tests
 * insert or otherwise change database state, there is no need for a teardown or cleanup
 * script.
 * <li>An {@link org.springframework.context.ApplicationContext ApplicationContext} is
 * also inherited and can be used for explicit bean lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
class OwnerServiceTests {

	@Autowired
	protected OwnerService ownerService;


	@Test
	void shouldFindOwnersByLastName() {
		Collection<Owner> owners = this.ownerService.findOwnerByLastName("Davis");
		Assertions.assertThat(owners.size()).isEqualTo(2);

		owners = this.ownerService.findOwnerByLastName("Daviss");
		Assertions.assertThat(owners.isEmpty()).isTrue();
	}

	@Test
	void shouldFindSingleOwnerWithPet() {
		Owner owner = this.ownerService.findOwnerById(1);
		Assertions.assertThat(owner.getLastName()).startsWith("Franklin");
		Assertions.assertThat(owner.getPets().size()).isEqualTo(1);
		Assertions.assertThat(owner.getPets().get(0).getType()).isNotNull();
		Assertions.assertThat(owner.getPets().get(0).getType().getName()).isEqualTo("cat");
	}

	@Test
	@Transactional
	public void shouldInsertOwner() {
		Collection<Owner> owners = this.ownerService.findOwnerByLastName("Schultz");
		int found = owners.size();

		Owner owner = new Owner();
		owner.setFirstName("Sam");
		owner.setLastName("Schultz");
		owner.setAddress("4, Evans Street");
		owner.setCity("Wollongong");
		owner.setTelephone("4444444444");
		owner.setMonedero(100);
                User user=new User();
                user.setUsername("Sam");
                user.setPassword("supersecretpassword");
                user.setEnabled(true);
                owner.setUser(user);                
                
		this.ownerService.saveOwner(owner);
		Assertions.assertThat(owner.getId().longValue()).isNotEqualTo(0);

		owners = this.ownerService.findOwnerByLastName("Schultz");
		Assertions.assertThat(owners.size()).isEqualTo(found + 1);
	}

	@Test
	@Transactional
	void shouldUpdateOwner() {
		Owner owner = this.ownerService.findOwnerById(1);
		String oldLastName = owner.getLastName();
		String newLastName = oldLastName + "X";

		owner.setLastName(newLastName);
		this.ownerService.saveOwner(owner);

		owner = this.ownerService.findOwnerById(1);
		Assertions.assertThat(owner.getLastName()).isEqualTo(newLastName);
	}
	
	@ParameterizedTest
	@CsvSource({
		"owner2", "owner3"
	})
	public void testFindOwnerByUsernamesvSource(final String username) {
		Owner owner = this.ownerService.findOwnerByUser(username);
		Assertions.assertThat(owner).isNotNull();
	}
	
	@ParameterizedTest
	@CsvSource({
		"owner124", "owner444"
	})
	public void testNotFindOwnerByUsermeCsvSource(final String username) {
		Owner owner = this.ownerService.findOwnerByUser(username);
		Assertions.assertThat(owner).isNull();
	}
	

}
