/*
 * Copyright 2014 Bitledger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bit.ledger;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Chris Beams
 * @since 0.1.0
 */
@ToString
@EqualsAndHashCode
public class TransactionOutput {

    private final double amount;
    private final KeyHash pubKeyHash;

    private TransactionOutput(double amount, KeyHash pubKeyHash) {
        this.amount = amount;
        this.pubKeyHash = pubKeyHash;
    }

    public static TransactionOutput of(double amount, KeyHash pubKeyHash) {
        return new TransactionOutput(amount, pubKeyHash);
    }
}