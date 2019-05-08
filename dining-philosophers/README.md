#Dining philosophers problem

The dining philosophers problem was invented by E. W. Dijkstra, a concurrency pioneer,
to clarify the notions of *deadlock* and *starvation freedom*

Imagine five philosophers who spend their lives just thinking and feasting. They
sit around a circular table with five chairs. The table has a big plate of rice. How-
ever, there are only five chopsticks (in the original formulation forks) available. 
Each philosopher thinks. When he gets hungry, he sits down
and picks up the two chopsticks that are closest to him. If a philosopher can pick
up both chopsticks, he can eat for a while. After a philosopher finishes eating, he
puts down the chopsticks and again starts to think.
