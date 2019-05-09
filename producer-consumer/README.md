# The producer-consumer problem

In computing, the *producer-consumer* problem (_also known as the bounded-buffer problem_) is a 
classic example of a multi-process synchronization problem. The problem describes two processes, the *producer* and the *consumer*, which share a common fixed buffer used as a queue.

- The producer's job is to generate data, put it into the buffer, and start again.
- At the same time, the consumer is consuming the data (i.e. removing it from the buffer), one piece at a time.

## Requirement

To make sure that the producer won't tr to add data into the buffer if it's full and that the consumer won't try to remove data from an empty buffer.
