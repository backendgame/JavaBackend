const BASE_URL = "http://localhost";
const TOKEN_NAME = "token";
const TABLES = "tables";

const TABLE_OFFSET = 5;

const BOOLEAN = 1
const BYTE = 10
const STATUS = 11
const PERMISSION = 12
const AVARTAR = 13
const SHORT = 20
const INTEGER = 40
const FLOAT = 41
const LONG = 80
const DOUBLE = 81
const STRING = 82
const TIMEMILI = 83
const BINARY = 100
const LIST = 101

const dataType = {
  BOOLEAN,
  BYTE,
  STATUS,
  PERMISSION,
  AVARTAR,
  SHORT,
  INTEGER,
  FLOAT,
  LONG,
  DOUBLE,
  STRING,
  TIMEMILI,
  BINARY,
  LIST
}

const dataTypeSize = {
  [BOOLEAN]: 1,
  [BYTE]: 1,
  [STATUS]: 1,
  [PERMISSION]: 1,
  [AVARTAR]: 1,
  [SHORT]: 2,
  [INTEGER]: 4,
  [FLOAT]: 4,
  [LONG]: 8,
  [DOUBLE]: 8, 
  [STRING]: 8,
  [TIMEMILI]: 8, 
  [BINARY]: 0,
  [LIST]: 0 
}

const isDataString = {
  [BOOLEAN]: false,
  [BYTE]: false,
  [STATUS]: false,
  [PERMISSION]: false,
  [AVARTAR]: false,
  [SHORT]: false,
  [INTEGER]: false,
  [FLOAT]: false,
  [LONG]: false,
  [DOUBLE]: false, 
  [STRING]: true,
  [TIMEMILI]: false, 
  [BINARY]: false,
  [LIST]: false
}

const statusIcon = {
  0: {
    icon: '🔘',
    description: ''
  },
  1: {
    icon: '⚪',
    description: ''
  },
  2: {
    icon: '🔵',
    description: ''
  },
  3: {
    icon: '🔴',
    description: ''
  },
  4: {
    icon: '⚫',
    description: ''
  },
  5: {
    icon: '🟢',
    description: ''
  },
  6: {
    icon: '🟡',
    description: ''
  },
  10: {
    icon: '☑️',
    description: 'Check Blue'
  },
  11: {
    icon: '✔️',
    description: 'Check Green'
  },
  12: {
    icon: '⚠️',
    description: ''
  },
  20: {
    icon: '🔒',
    description: 'Lock'
  },
  21: {
    icon: '🔐',
    description: 'Lock With Key'
  },
  22: {
    icon: '🔏',
    description: 'Lock with Pen'
  },
  29: {
    icon: '🔓',
    description: 'Unlock'
  },
  30: {
    icon: '🔑',
    description: 'Key'
  },
  31: {
    icon: '🗝️',
    description: 'Old Key'
  },
  32: {
    icon: '🆕',
    description: 'New'
  },
  40: {
    icon: '⛔',
    description: ''
  },
  41: {
    icon: '🚫',
    description: ''
  },
  42: {
    icon: '🛑',
    description: ''
  }
}

const permissionIcon = {
  0: {
    icon: 'None',
    description: ''
  },
  1: {
    icon: 'Read Only',
    description: ''
  },
  2: {
    icon: 'Write',
    description: ''
  },
  3: {
    icon: 'Admin',
    description: ''
  },
  4: {
    icon: 'User',
    description: ''
  }
}

const avatarIcon = {
  1: {
    icon: '👶',
    description: 'Baby'
  },
  2: {
    icon: '🧒',
    description: 'Child'
  },
  3: {
    icon: '👦',
    description: 'Boy'
  },
  4: {
    icon: '👧',
    description: 'Girl'
  },
  5: {
    icon: '🧑',
    description: 'Person'
  },
  6: {
    icon: '👱',
    description: 'Person: Blond Hair'
  },
  7: {
    icon: '👨',
    description: 'Man'
  },
  8: {
    icon: '🧔',
    description: 'Person: Beard'
  },
  9: {
    icon: '👨‍🦰',
    description: 'Man: Red Hair'
  },
  10: {
    icon: '👨‍🦱',
    description: 'Man: Curly Hair'
  },
  11: {
    icon: '👨‍🦳',
    description: 'Man: White Hair'
  },
  12: {
    icon: '👨‍🦲',
    description: 'Man: Bald'
  },
  13: {
    icon: '👩',
    description: 'Woman'
  },
  14: {
    icon: '👩‍🦰',
    description: 'Woman: Red Hair'
  },
  15: {
    icon: '🧑‍🦰',
    description: 'Person: Red Hair'
  },
  16: {
    icon: '👩‍🦱',
    description: 'Woman: Curly Hair'
  },
  17: {
    icon: '🧑‍🦱',
    description: 'Person: Curly Hair'
  },
  18: {
    icon: '👩‍🦳',
    description: 'Woman: White Hair'
  },
  19: {
    icon: '🧑‍🦳',
    description: 'Person: White Hair'
  },
  20: {
    icon: '👩‍🦲',
    description: 'Woman: Bald'
  },
  21: {
    icon: '🧑‍🦲',
    description: 'Person: Bald'
  },
  22: {
    icon: '👱‍♀️',
    description: 'Woman: Blond Hair'
  },
  23: {
    icon: '👱‍♂️',
    description: 'Man: Blond Hair'
  },
  24: {
    icon: '🧓',
    description: 'Older Person'
  },
  25: {
    icon: '👴',
    description: 'Old Man'
  },
  26: {
    icon: '👵',
    description: 'Old Woman'
  }
}

export {
  TABLE_OFFSET,
  BASE_URL,
  TABLES,
  TOKEN_NAME,
  dataType,
  dataTypeSize,
  isDataString,
  statusIcon, permissionIcon,
  avatarIcon
}