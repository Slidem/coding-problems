function random(numbers, n) {
  if (!numbers) {
    return Math.random(n);
  }

  const selectRandomFrom = [];
  for (let i = 0; i < n; i++) {
    if (numbers.includes(i)) {
      continue;
    }
    selectRandomFrom.push(i);
  }

  return selectRandomFrom[Math.floor(Math.random() * selectRandomFrom.length)];
}

console.log(random([1, 3, 4, 5], 6)); // should print only 0 and 2
