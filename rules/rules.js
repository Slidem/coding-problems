const points = {};

function validateRules(ruleStrings) {
  if (!ruleStrings) {
    return true;
  }

  ruleStrings.forEach(ruleStr => validateRules(ruleStr));
}

function validateRules(ruleStr) {
  const pointsAndRule = rulesStr.split(" ");
  const pointA = pointsAndRule[0];
  const pointB = pointsAndRule[2];

  const rule = pointsAndRule[1];
  const invertedRule = invertRule(rule);

  // Add to points map; Each time you add a new point,
  // if a rule is more genral (West, East, South, Nord, only), and there is already a more constrained rule defined, ignore it.
  // otherwise if a more constrained rule is found, pdate all existing nodes with this new rule. (delete old place, update with existing one).
  // only after constructing all points, check if there are duplicate points in coordinates
}

function invertRule(r) {
  const firstChar = r.charAt(0);
  const secondChar = r.length === 1 ? "" : r.charAt(1);
  return invertRuleChar(firstChar) + invertRuleChar(secondChar);
}

function invertRuleChar(ruleChar) {
  switch (ruleChar) {
    case "N":
      return "S";
    case "S":
      return "N";
    case "W":
      return "E";
    case "E":
      return "W";
    case "":
      return "";
  }
  throw Error("Invalid rule char. Expected one of : N, W, S, E");
}
