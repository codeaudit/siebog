/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under
 * the Apache License, Version 2.0 (the "License") you may not
 * use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */

package siebog.test.dnars.inference

import org.junit.Assert.assertEquals
import org.junit.Test

import siebog.dnars.base.AtomicTerm
import siebog.dnars.base.StatementParser
import siebog.dnars.base.Term
import siebog.dnars.graph.DNarsGraph
import siebog.dnars.graph.DNarsGraphFactory
import siebog.dnars.inference.ResolutionEngine
import siebog.test.dnars.DNarsTestUtils.TEST_KEYSPACE
import siebog.test.dnars.DNarsTestUtils.createAndAdd

/**
 *
 * @author <a href="mitrovic.dejan@gmail.com">Dejan Mitrovic</a>
 */
class ResolutionTest {
	@Test
	def testAnswer: Unit = {
		val graph = DNarsGraphFactory.create(TEST_KEYSPACE, null)
		try {
			createAndAdd(graph, // @formatter:off 
				"cat -> animal (1.0, 0.9)",
				"developer ~ job (1.0, 0.9)") // @formatter:on

			assertAnswer(graph, "? -> cat", null)
			assertAnswer(graph, "? -> animal", AtomicTerm("cat"))
			assertAnswer(graph, "cat -> ?", AtomicTerm("animal"))
			assertAnswer(graph, "animal -> ?", null)
			assertAnswer(graph, "water -> ?", null)
			assertAnswer(graph, "developer ~ ?", AtomicTerm("job"))
			assertAnswer(graph, "? ~ developer", AtomicTerm("job"))
		} finally {
			graph.shutdown
			graph.clear
		}
	}

	def assertAnswer(graph: DNarsGraph, question: String, answer: Term): Unit = {
		val a = new ResolutionEngine(graph).answer(StatementParser(question))
		if (a == None)
			assertEquals(answer, null)
		else
			assertEquals(answer, a.get)
	}
}