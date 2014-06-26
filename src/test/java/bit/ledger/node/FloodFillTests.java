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

package bit.ledger.node;

import org.junit.Test;

import org.zeromq.ZThread;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

import static org.zeromq.ZMQ.Socket;
import static org.zeromq.ZMsg.*;

public class FloodFillTests {

    /*

    first node comes online.

    several Items are added to its (ordered) Collection (intentionally being generic here)

    second node comes online.

    second node "discovers" first (is directly supplied with its address), and adds it as a peer.

    second negotiates with first to receive all Items.

    --> wait in loop, asserting for both nodes to have identical information. <--

    add an Item to second.

    second broadcasts new Item to first.

    --> wait in loop, asserting for both nodes to have identical information. <--

     */

    //public static void main(String... args) throws InterruptedException {
    @Test
    public void test() throws InterruptedException {

        Network network = new Network();

        {
            // create and start a node
            Node node = new Node(network);
            ZThread.start(node);

            Socket requestSocket = node.createRequestSocket();

            // should have no items at start
            newStringMsg("count").send(requestSocket);
            assertThat(recvMsg(requestSocket).popString(), is("0"));

            newStringMsg("item:1").send(requestSocket);
            assertThat(recvMsg(requestSocket).popString(), equalTo("ACK"));

            newStringMsg("item:2").send(requestSocket);
            assertThat(recvMsg(requestSocket).popString(), equalTo("ACK"));

            newStringMsg("count").send(requestSocket);
            assertThat(recvMsg(requestSocket).popString(), is("2"));

            newStringMsg("get:2").send(requestSocket);
            assertThat(recvMsg(requestSocket).popString(), equalTo("item:2"));
        }

        Node node2 = new Node(network);
        ZThread.start(node2);
    }

}
