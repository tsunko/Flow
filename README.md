# Flow
A dead-simple and easy to implement command system. 

## Getting Started

Simply call `Faucet.initialize(null)` for the default implementation of Flow.

In your application, implement the `Invoker` interface as you see fit. 
Optionally, you may choose to implement the Channel object, however, it is not neccessary. 
Your `Invoker` implementation can simply return null or a nulled Channel implementation for its `getChannel()` implementation, as Flow by itself does not utilize `Channel`. 

From there, wherever your application listens for input messages/commands, call `Faucet.process(String, Invoker, Flow)`.

The first argument refers to the name of the command. The second is an instance of your `Invoker` implementation.
Lastly, the third argument (Flow) can be your own Flow implementation or the default, included `StringFlow` implementation, 
which accepts arguments as a `String[]` for its constructor (excluding the command).
