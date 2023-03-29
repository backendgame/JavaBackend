package backendgame.config;

import java.util.Random;

public class CipherBinary {
	private Random random;
	public short maskLength;
	private byte[] maskCrypto;
	
	public CipherBinary() {
		init(new byte[] {45,-37,-34,120,-22,-113,-69,34,-40,108,-1,-4,80,-79,113,4,-50,107,105,25,-48,-99,-42,-65,73,-120,20,-120,-66,-128,-54,-72,16,53,41,78,-24,-119,12,-101,-66,-57,-38,51,76,31,85,32,-27,67,61,-81,-5,41,-15,-125,-48,89,97,-72,-66,-99,65,-93,-73,53,-97,83,59,122,2,-35,-112,-118,-103,-22,-80,-43,1,17,-12,91,-123,49,117,84,10,7,-88,124,122,59,-22,-107,5,-64,-52,19,-59,102,-126,-26,-121,110,-64,-48,68,-7,-8,-65,28,40,57,86,-100,-73,3,-100,-107,-38,35,-33,78,1,-6,42,101,109,63,-60,53,99,76,111,-41,-46,-95,103,40,124,-72,95,-77,105,117,-77,-86,-8,-96,-43,72,42,-74,-37,110,-47,-7,-127,-62,-55,-61,-2,122,-63,-97,-70,-128,102,-21,-90,-103,26,20,-5,104,-21,108,-97,-86,71,-4,62,117,-7,124,25,-104,126,-57,-71,-44,71,78,82,49,98,114,46,48,46,58,-39,-112,113,71,27,-85,97,104,-51,104,122,-8,96,-128,-118,-78,-4,-84,94,-105,-47,21,-56,-99,73,-22,-100,50,123,-61,-1,-113,52,-41,-77,29,91,-121,-74,-73,100,20,-104,43,-62,-90,6,60,-89,-7,50,113,-33,-106,-14,-83,-97,126,102,65,-10,116,20,-51,98,-53,71,120,-123,-4,-98,40,-3,47,108,96,105,114,87,31,-119,-24,-122,-79,-106,-76,-7,67,-124,-62,102,114,-60,39,98,61,-102,-2,104,-50,3,-31,36,-110,-85,-11,34,22,48,-113,38,5,-127,-120,109,-122,30,102,-61,102,-16,-51,33,-113,-44,30,17,11,70,44,-102,-87,-100,-72,4,78,30,-128,-28,-2,-28,-26,35,-68,-116,-122,112,116,56,32,-76,-33,10,75,-19,-45,-87,-88,2,-58,62,78,2,38,-126,19,-112,-72,-55,74,-4,-127,62,47,5,-73,9,125,117,-46,-74,-122,-5,-64,-100,124,70,113,-10,-119,-92,-28,-110,-49,-91,119,-80,80,126,-79,36,-37,122,56,-99,83,45,-117,-123,-70,88,77,67,102,80,-38,-107,-18,65,11,6,49,60,59,-33,31,-53,97,-100,-35,-74,32,-11,-92,103,114,-98,100,-115,116,30,-30,-2,99,117,-80,126,69,-95,2,58,59,-8,-5,107,-119,55,41,-64,-66,49,77,-87,108,-84,123,-119,-79,126,-9,27,-51,115,7,-87,-23,56,116,7,-36,-55,-54,-107,13,-53,27,-12,-18,-98,106,-92,-3,33,-32,56,4,-44,34,-124,45,76,31,51,-101,38,40,105,-122,-124,12,88,-66,46,-28,125,-125,53,-34,-105,109,16,-71,-47,-99,-126,88,-102,-41,-119,-49,-119,79,-23,105,64,3,-22,36,-81,54,96,-1,-54,-49,-57,67,-103,16,82,104,-97,111,23,-23,-5,-75,-69,50,-60,-36,12,-83,39,55,-33,-118,117,-48,35,-100,8,-57,59,71,-70,52,58,39,120,30,25,44,-91,63,-8,-30,37,111,-58,-99,58,-116,89,-74,-60,55,-70,-98,-109,-18,28,72,88,95,-107,-116,104,-37,-68,-101,39,-120,-85,-94,61,-114,104,-29,24,-119,-69,-52,17,99,-42,118,66,86,-38,43,126,-15,-63,-62,-5,-12,-17,-75,103,-15,12,77,-124,32,109,21,-113,-15,-41,-22,32,-45,-79,123,-6,98,16,-124,-49,-49,-7,89,-2,-113,-13,-48,-112,-46,25,-60,127,105,114,-114,71,42,-17,127,30,94,72,-99,111,-77,-20,-63,13,-112,-121,-62,99,-113,-5,-16,1,34,103,-105,77,25,-61,-112,-81,124,-44,-49,117,-33,-86,-44,-19,-77,-74,20,-83,-92,90,-117,-42,116,-117,-57,-114,-70,111,34,-83,39,23,91,41,61,100,-115,36,36,-34,-16,-55,-20,-117,46,-121,47,19,104,-20,-78,99,83,-56,-99,39,-22,56,38,-116,56,-55,-63,67,-81,101,-124,116,64,109,-97,50,36,-11,-16,74,-71,-73,-9,-55,-24,111,-1,7,-5,-21,26,32,-43,-73,-34,-82,-103,117,54,3,51,98,-117,-16,-50,38,-14,-79,84,15,1,-104,90,-116,-110,-37,88,103,66,78,-44,12,2,-2,-85,31,-96,-80,-55,-125,113,98,64,7,-62,48,-64,-74,100,-51,62,96,40,21,102,-103,110,-123,-101,24,67,2,61,25,-18,43,21,10,96,7,83,-110,127,21,47,126,-21,15,-10,95,-2,-21,-81,-67,-32,32,-26,-75,89,122,-94,-79,96,-61,98,-69,122,34,-69,36,-61,-72,-63,-51,18,-72,12,96,-97,97,19,39,18,-33,-99,23,-101,112,67,26,8,-85,-103,88,12,99,10,104,-56,-92,22,-71,-91,28,26,58,1,-98,-20,-3,-55,-72,-54,-121,-79,102,8,111,61,123,85,65,-109,-98,-109,-19,74,-109,116,66,-4,12,-108,7,-109,111,117,-118,-58,51,49,-24,41,126,-49,-15,51,-58,-42,96,-97,-77,37,2,-9,90,56,87,54,-27,-71,61,-63,-88,101,-64,82,53,-82,-41,-2,-18,112,100,-46,86,99,97,-77,104,35,-94,13,-109,-17,23,-109,-35,56,34,124,23,56,-43,-62,37,-107,28,86,107,70,-6,-19,-50,-40,19,-84,-38,-71,44,-111,-107,-89,71,-55,115,39,-75,-85,35,-119,-79,-33,-76,-22,-114,50,14,38,26,75,-117,-15,-26,-9,108,46,-90,-13,-102,-84,76,-18,52,57,118,-122,59,-110,-51,-24,-62,-94,59,-76,2,-55,59,69,85,91,38,-112,93,108,48,40,-99,-45,23,-74,120,121,-4,51,-92,-116,38,-19,-43,-73,122,-42,17,82,87,100,63,-125,-109,-76,-13,113,17,-73,-101,-116,-68,-83,-98,109,-93,89,75,-123,-119,-118,-102,-84,-6,27,127,58,61,101,-32,-49,-24,-18,48,57,90,61,-108,98,127,7,99,-78,-51,89,-36,17,66,19,-41,92,-111,99,29,-38,-65,33,-34,-36,-28,105,-64,41,-42,98,3,4,-72,33,80,124,41,-6,118,5,-67,33,61,26,79,113,19,110,-71,-15,-124,-58,27,-125,-17,64,-104,26,-34,58,8,103,119,109,-53,-91,123,-22,44,-65,83,40,37,-96,4,3,-89,-47,-63,51,33,-85,-112,89,16,-70,-65,-87,-52,-107,104,-73,-29,-74,96,46,-55,-111,-126,119,82,-71,16,75,-56,-108,67,-12,98,-24,83,-51,108,-53,-117,117,117,-78,-11,73,-3,89,-118,12,-108,-100,116,-50,44,-76,-44,30,-76,-2,18,-56,47,95,-40,-40,36,60,34,61,75,-46,-127,107,68,76,45,-23,-104,-54,-15,22,56,124,104,-1,-106,94,-30,63,101,-40,-113,-76,-34,-121,-1,36,-62,-85,-14,-58,-56,2,-125,17,-107,-35,33,-22,-21,-79,-23,-15,62,75,96,-51,-9,-28,40,-124,-54,72,-33,14,-12,54,116,-82,-3,-29,-50,40,-109,-30,-8,-84,120,-83,-88,-5,-61,-70,1,97,9,18,-83,-2,-117,31,79,-54,30,126,-98,116,68,-70,32,56,-21,-25,42,-88,-110,-66,10,35,115,122,123,-2,41,-12,70,-106,50,112,-37,60,6,-19,81,-88,58,98,-94,-118,70,6,95,-109,-119,-112,91,45,-32,37,18,33,47,-14,-46,-23,-43,85,12,87,-71,21,18,-31,-11,-81,-64,86,20,15,-70,-128,-88,48,-55,120,26,10,93,-25,45,105,31,47,46,60,-47,-72,-17,52,67,-63,-96,72,-64,125,30,-54,9,23,-106,82,65,91,8,-15,-103,122,-63,83,-23,49,45,34,-114,-34,-95,112,-68,-86,71,-51,99,51,-122,98,-100,127,-4,38,-41,102,89,47,-125,-20,19,40,-99,-78,-3,-65,-34,-73,89,-84,-87,-122,79,26,9,96,111,-63,-27,24,24,81,-14,127,36,-78,122,-110,-115,93,-20,-89,72,87,-80,13,-92,118,-10,11,-41,-92,97,65,92,-69,52,-98,4,-109,72,-99,79,22,66,34,-124,-35,106,120,-48,-59,13,-71,-90,36,-45,86,26,-11,-54,49,-69,110,14,26,35,72,112,-60,13,43,-84,-121,-51,71,46,-105,-52,31,-49,-113,8,6,111,8,-18,-38,-57,99,-75,-119,46,-47,53,-78,69,-96,-61,-87,26,59,127,87,-7,-64,-64,-114,-70,30,120,110,28,-124,95,-12,48,24,103,-27,67,5,71,-41,-47,13,-127,-37,-66,80,-21,59,11,104,-32,69,-102,70,75,40,-38,35,-19,-55,-63,-9,84,21,-128,-30,-52,90,-80,-100,26,87,58,61,110,-105,71,-122,120,79,-84,-15,-115,11,-61,-117,112,102,78,-53,58,-79,-28,63,-127,35,-41,35,-88,108,122,121,-39,-44,8,-11,-83,90,-56,-28,-7,-87,-27,90,70,32,74,-117,54,47,-43,15,-52,-85,45,43,8,-116,17,-69,79,-8,-13,95,18,-19,-113,43,-88,-38,88,112,-71,1,-41,65,-123,-33,16,12,-55,124,-89,-59,55,-65,-92,37,-20,-11,77,9,-51,-6,-61,-37,105,-104,-70,100,-42,-108,100,-62,-10,-123,98,-53,-80,11,-4,-39,28,-37,71,31,5,-4,-99,75,106,-77,-96,123,22,22,-25,63,-53,-102,66,37,23,70,-33,14,48,85,-121,5,68,70,124,-11,-58,-18,-96,-115,-43,-14,-75,61,72,80,-106,-88,109,16,64,118,76,15,55,38,95,115,-119,-21,-35,6,-116,74,-16,-104,-90,-5,121,-23,115,21,-26,54,-34,-57,-124,12,-122,-85,49,84,-72,102,-39,-24,-46,2,-126,-30,68,11,-46,32,-7,-18,38,-17,-36,72,-30,22,23,116,67,-31,121,-83,44,89,-76,-89,33,111,-16,-51,34,104,-93,90,-119,31,121,95,125,-96,99,-60,-101,-103,6,-104,103,84,-124,-83,93,17,-118,44,45,94,69,-99,-34,-109,-128,-61,-55,-89,-2,-95,54,-7,-121,81,-66,119,7,59,24,-53,88,32,-79,-108,-73,-35,-32,103,-22,9,-34,118,-1,-7,118,23,-55,31,37,15,-54,26,-39,2,82,6,124,8,50,44,102,30,-81,115,-89,91,93,103,-38,-120,-99,56,-91,-82,-35,-113,-23,-11,82,21,-100,-30,-112,-81,-106,19,-53,-35,63,-82,124,-105,48,12,116,-97,21,68,-47,-39,58,-99,41,110,-122,74,114,-37,-40,75,-64,65,5,-79,-22,-86,-93,-59,-98,-96,50,52,-41,-36,119,119,25,82,-7,123,108,40,-74,-46,4,54,-87,64,25,-60,26,-38,76,-30,22,-26,49,-123,-86,-8,-28,13,95,91,98,61,-98,35,33,7,8,66,36,13,-35,-38,17,86,-32,-69,-6,63,108,-118,-34,-25,-117,60,-118,66,-46,80,-26,-70,-33,87,26,-83,32,55,-75,123,-19,112,120,-80,11,105,44,-106,5,-103,-35,-123,83,-28,-91,76,68,65,55,27,103,-77,-16,118,-86,82,81,63,-57,-64,-48,-19,77,-20,-79,-16,114,-71,-59,60,-115,-18,-55,-74,38,-106,78,77,81,-37,66,104,38,-52,59,-91,-1,-24,-7,-73,119,46,-8,-25,-92,55,113,-30,18,6,-111,-46,12,127,123,41,64,-13,-65,103,-112,-64,115,14,-108,-117,-35,62,32,-86,-63,11,-36,-42,-127,-101,-81,-32,-116,100,-10,18,64,-1,-112,72,-92,-63,-92,113,109,65,50,-4,-109,-31,-116,-93,-50,110,-47,-101,-5,-40,-67,110,110,11,48,13,64,-31,96,-94,79,-111,-122,-9,-2,-4,-61,41,55,-41,76,111,7,-70,126,11,-1,-7,96,-74,90,35,79,-109,-47,-119,124,-8,-88,-106,-24,-2,-29,2,-85,110,49,-96,-124,90,-38,-104,98,76,-78,54,76,126,122,-27,81,101,124,-36,-105,70,-54,73,125,112,-105,-7,-87,117,-75,13,-15,69,127,45,-124,-26,-15,81,-102,-100,47,101,-25,-5,-100,12,-107,11,67,3,-25,107,11,8,112,84,-85,61,-46,-41,12,32,-122,69,76,-113,74,-45,-122,2,62,-71,87,-54,27,69,87,-103,53,124,95,100,117,73,-12,11,8,28,-61,-119,-37,-7,-49,-60,45,-18,110,-96,54,-53,48,9,-7,-45,22,-123,-43,99,-125,-112,-83,-128,112,67,95,13,57,3,71,80,-70,39,-120,53,-98,80,-8,-14,82,124,19,-100,-58,64,56,110,-109,-123,112,33,73,-28,-86,84,-125,126,-71,70,27,122,-71,14,120,84,-65,-25,-4,101,-14,-72,-21,-64,-103,-71,-98,-13,92,-30,-92,18,64,-114,-110,-66,-23,40,-8,-24,-51,106,50,-14,-105,-120,77,29,-15,-15,-109,34,-53,-38,118,44,-101,-72,95,-58,-115,-38,-103,23,-116,124,-112,-11,-20,-95,-57,-12,121,80,-60,-39,-85,-124,-92,51,99,55,122,7,-105,-89,14,65,58,-46,96,-4,2,-42,78,-12,-74,4,79,-36,-55,89,94,-20,65,-76,-83,-27,-67,-38,-3,23,74,36,-110,-18,-59,-95,12,-40,90,103,-102,-73,-107,-87,-108,-55,27,-1,89,-31,-16,90,-89,73,-22,-73,-38,-113,-63,-6,63,54,4,36,-10,-98,26,-81,60,-49,107,119,38,22,-108,-5,-26,123,122,-115,113,-83,-40,-89,-26,-13,79,92,79,-68,-25,99,85,-51,37,40,117,-46,-14,46,-36,57,-59,41,81,79,-42,-56,114,-26,115,-110,78,-76,-75,-76,99,110,-57,12,-64,-58,64,45,27,-68,69,37,31,-66,118,73,115,-65,25,72,57,50,67,-34,108,-8,-83,52,119,105,-12,-24,-29,65,-126,19,-70,122,-100,-128,-97,-115,-92,-108,-31,111,87,-91,112,18,-27,-64,-107,-88,60,99,-72,-74,-76,95,35,-117,110,34,-11,42,89,33,92,-72,-28,27,-80,94,-40,-12,51,-109,-101,71,-97,-88,84,38,-115,27,-5,78,-35,91,-110,65,49,53,-122,49,8,-41,-32,-55,-98,-57,69,-50,-72,118,-95,68,-44,110,-102,107,104,31,-29,46,-40,-24,24,-23,78,16,102,-50,41,-82,-115,91,91,58,-34,5,45,-100,21,5,-14,-83,-20,-119,-109,45,-83,-2,-27,-11,-63,50,-48,53,-47,86,2,-11,-11,61,85,32,39,-41,-76,-56,6,-80,-52,-117,-111,41,-33,-6,-47,78,50,-60,78,36,-59,-122,56,-78,-56,-42,-65,6,85,122,86,-120,-33,-80,-1,83,-19,26,120,10,87,57,56,89,29,-63,14,31,77,6,91,-77,-8,107,82,-50,122,32,66,67,67,61,70,-16,12,-89,25,110,74,-53,121,56,65,-74,-69,-80,-127,-69,20,13,89,-68,-35,-73,59,-74,-110,-50,-107,-55,-14,-89,-18,-28,-54,120,126,-107,21,63,-87,-110,-21,-62,-76,28,70,-28,-121,-101,71,45,-80,-52,106,-20,99,-94,-28,-101,-33,61,29,-93,116,-70,-16,101,-122,-73,-18,-52,120,-16,56,20,56,36,17,-66,16,-72,108,25,-82,-55,72,71,-124,-74,-83,67,-27,4,111,-6,20,-11,75,-60,-116,-12,-16,-108,-124,-25,-54,-3,-26,114,31,-100,112,101,-95,109,-36,118,-49,110,-33,112,-72,120,59,30,-96,67,-88,-12,-1,-28,-125,59,-111,6,-113,-103,-73,50,59,-83,60,-15,-51,-48,101,75,47,23,60,59,-86,-14,-87,18,79,117,69,106,102,-28,-99,-40,108,40,117,8,-68,-53,-34,-52,100,-122,71,-63,-49,120,107,-40,81,-106,-27,61,-98,80,-40,-119,-59,-50,-126,47,-3,127,33,-10,-21,37,-29,113,-53,-99,-71,-58,50,83,47,38,-100,-99,-33,-68,2,-33,42,54,92,115,-94,123,109,-47,-37,-91,-10,-12,-81,90,-19,124,8,-114,35,-2,-30,-128,-117,-31,-4,-51,38,66,-37,-90,-128,-16,-83,-98,-98,-79,-35,44,-65,12,-10,-76,111,26,-126,100,89,4,-1,-95,-85,-92,-59,63,117,-106,-76,121,-49,-104,123,-58,6,-123,110,30,60,-114,-120,1,-19,30,-72,-103,30,121,-112,53,-39,98,64,93,118,-98,-21,-86,-82,20,-34,127,80,-11,-26,81,89,91,31,23,-6,67,-75,4,-124,92,-44,97,-87,-35,44,55,-85,20,65,108,60,-76,34,78,-41,-19,3,73,-3,-99,43,1,98,117,-95,25,47,9,-118,100,-100,-101,119,113,76,111,-78,-110,-121,-89,65,-23,-90,53,8,28,40,32,-116,-44,80,22,119,-110,-36,-35,-24,108,53,53,72,-15,-103,4,-121,1,-47,-124,-38,-73,-108,-34,-70,-26,105,-13,-107,-26,-69,98,-32,49,-52,-68,-99,70,19,-57,-82,-118,-83,-79,-122,-92,124,10,-110,-100,-45,-79,17,-39,107,-80,-35,-25,46,114,-7,-27,103,-126,-5,7,37,-42,75,98,-85,20,61,-94,-112,97,47,21,-63,-20,95,-95,90,-58,93,111,45,81,36,101,79,-96,85,54,36,81,23,15,-60,95,-126,-81,-127,-94,79,23,19,104,90,3,-47,100,-54,-59,-68,-70,2,-76,-91,-70,-75,77,95,-104,14,88,4,-22,100,-105,-109,67,51,-6,-106,-38,127,-1,86,121,-43,-48,3,97,105,-113,-41,-63,25,39,-127,100,-11,126,-121,58,-26,-81,-65,75,111,87,30,-57,-23,-108,12,108,-17,-91,-18,-98,84,2,47,-17,109,-37,-107,-5,-70,11,-111,30,-19,79,-80,-90,127,5,-54,-117,6,64,-4,79,109,-46,48,53,-17,8,47,26,-83,-60,-58,-72,-20,88,-58,-127,47,-24,31,63,-29,6,124,-31,-42,4,-4,35,88,97,-108,-41,53,-114,-101,-78,47,90,82,78,-42,98,-114,-78,-62,10,66,-55,-34,8,86,-105,51,98,17,46,-39,-67,-53,28,39,-128,40,-88,90,-92,90,-123,-96,-127,106,-36,95,-47,-40,-31,48,-56,98,-111,79,-5,-83,-33,23,18,-74,-26,-111,35,21,22,-19,-49,50,61,93,118,46,-30,-124,-103,35,91,127,36,89,89,70,-65,-55,-37,-95,20,121,-114,48,59,57,-34,78,-111,114,112,11,19,25,97,-45,105,-42,-5,106,111,86,84,27,24,-125,-49,49,-19,117,-90,92,-23,115,74,11,-10,45,-108,-74,64,37,25,26,38,38,-30,124,102,109,-70,-92,8,107,114,52,-119,123,112,-98,-90,42,98,71,-42,106,107,-104,-49,-11,-59,9,-46,-115,-70,-49,-10,-38,120,-62,90,-42,81,-104,24,-117,7,97,-5,-104,-51,81,19,38,95,76,123,-18,58,125,71,-101,38,-79,-54,52,83,-11,-124,-116,-71,-12,-74,56,-119,-65,-116,-92,88,126,18,116,12,91,-82,39,-86,14,58,24,-25,116,83,124,71,-57,17,99,117,85,103,82,43,-29,-17,-116,-59,116,1,60,86,-2,26,-120,-73,69,-56,-58,19,-13,-70,94,89,126,-36,-60,-5,-123,115,74,-79,-99,71,69,-34,-116,-1,56,41,-67,74,15,-83,-93,-128,99,-46,-20,4,25,92,-39,103,122,99,105,61,-71,-64,93,32,-66,-21,-74,98,13,27,-67,-30,-68,90,23,-19,-121,88,17,86,104,19,-67,-39,-88,-68,-89,-17,89,-12,113,-56,-48,-12,4,43,123,-93,47,10,-63,5,96,26,111,-60,-10,-90,-3,32,78,-45,28,101,111,19,-68,74,24,-58,38,71,-4,65,-96,-25,-5,14,1,103,-15,36,19,78,60,44,73,95,-5,-28,-56,-81,63,-47,11,17,-121,82,-22,-21,-20,-93,89,62,49,97,-108,12,-38,37,-6,-4,-96,-86,-47,70,-120,33,-60,90,38,71,37,35,44,77,53,54,41,-119,3,-41,-53,1,-33,92,46,84,124,-38,55,46,52,1,-73,42,-76,-2,-127,6,-59,51,68,40,-28,75,-124,-21,14,109,53,-128,70,-37,-117,-97,75,15,-71,60,-28,33,48,-49,40,-8,-108,70,122,52,-53,108,-103,12,-123,17,-17,-2,-109,112,20,-69,-31,83,-25,-25,-92,-85,33,55,-23,-84,63,-28,66,16,22,27,-103,50,90,64,-53,-24,3,88,-60,64,-102,36,57,70,-24,55,126,-14,-27,-115,-50,-20,33,42,11,45,-28,-11,-38,-35,-90,94,121,57,-65,76,-62,-108,88,41,81,41,-57,111,109,-5,3,6,111,-97,52,118,-109,-19,-74,-105,13,-68,-45,-12,-42,-123,43,118,-4,57,88,120,-118,-115,-87,-69,32,-15,-42,-26,65,-62,-8,-128,-35,114,46,6,-58,-45,98,6,44,74,87,94,61,62,98,-43,125,34,118,-69,60,115,3,2,53,-12,108,-88,-26,-16,63,-61,27,-40,93,-84,79,49,71,-37,42,59,105,-51,-78,-98,-49,-14,-121,122,-70,-86,16,-20,-71,82,-87,63,1,-106,124,-128,-101,-27,55,16,-109,-113,-55,-35,-72,29,102,-24,-17,-85,96,35,-89,76,54,-36,21,113,-41,32,-60,127,71,22,67,-50,-71,-112,6,-102,-58,-83,74,-102,-86,116,-28,104,40,29,-56,-32,-71,-83,67,-14,-2,116,22,-101,99,62,41,-18,-65,112,75,33,-35,-30,53,4,127,23,100,28,-100,-45,-126,-67,59,-101,-97,68,-14,-1,47,-108,-7,-123,-122,-115,8,-10,124,126,-97,118,-97,-24,7,92,-88,-15,-37,9,40,125,-77,-97,-99,37,106,-105,-97,-25,12,65,-65,92,-72,-29,90,-63,29,-2,107,88,-26,8,3,-19,-40,-120,71,-45,-104,20,-97,-73,-28,-66,99,92,-80,57,-25,113,28,-128,53,19,77,121,-15,30,27,-114,88,-21,-78,55,27,68,14,21,-104,26,-117,27,56,-101,-2,-36,13,-20,56,30,-42,40,-80,45,-128,75,35,-59,-97,18,117,-29,6,-74,-52,-52,-32,79,7,84,122,-36,10,-42,-66,10,-25,-24,-46,115,-46,-41,38,99,111,-102,126,-92,-18,87,-111,52,-40,-65,89,-89,-6,-64,-30,73,59,-69,122,-37,-41,79,-94,-77,-102,-108,-20,7,120,104,-39,117,-108,-127,-38,86,10,86,-10,-99,-59,-25,30,42,-7,-62,88,4,28,102,-78,91,42,103,-117,-9,-88,-40,-85,-35,-82,-55,-19,99,79,124,112,-106,77,-6,-127,69,-38,-92,-21,45,-108,-76,5,-53,103,-111,55,98,105,82,67,2,-126,111,-99,-75,-16,104,-18,-38,23,-115,72,-120,-122,19,57,-3,60,67,9,-65,-31,46,-22,-115,-96,31,30,66,-46,23,-22,69,-14,88,-66,-75,22,-90,74,70,-99,-104,30,-49,-125,-127,-112,-21,-57,-107,113,-1,-39,-6,-58,-65,57,22,8,-103,55,17,125,-41,-109,86,-64,-91,-68,85,-127,3,83,-28,122,-16,-46,-71,-37,-103,-125,111,-37,-110,-111,-106,111,-110,17,33,70,29,-32,112,-83,101,-45,113,94,-65,-32,65,27,1,-114,-97,90,20,32,71,11,-14,-14,43,-41,-49,43,-75,-20,122,-11,12,-120,-33,119,-71,6,-60,99,115,-63,-124,-104,-92,117,82,-56,87,104,-31,113,70,-69,-59,-128,38,-5,71,50,-66,110,39,69,76,29,-30,-8,66,101,68,-96,108,-12,78,-113,-81,92,-126,-26,-127,-60,119,82,-23,-111,-72,24,64,32,121,95,76,16,81,66,117,-59,39,106,-73,-68,88,107,47,105,-122,39,8,118,-82,73,-11,93,-52,84,-10,-19,-89,-106,95,-82,-71,18,-84,104,19,63,-63,16,-100,86,-104,46,55,-116,-46,-116,-73,-125,70,123,-75,16,112,-38,-26,75,70,-27,-23,64,-20,-8,-79,-109,98,-18,-77,-89,-112,80,119,-25,-72,112,122,107,50,108,-11,112,119,68,2,42,16,15,-60,-9,71,-109,-3,-14,84,-85,-12,3,-92,41,3,-40,-30,23,6,-96,-64,-125,-8,83,109,104,60,40,-100,-94,-67,-66,-36,8,-33,-31,122,-73,-122,-9,-120,69,-120,93,-35,-74,-63,-57,-33,75,-52,-46,-87,-71,73,10,102,-5,-58,32,39,10,113,15,58,-91,-94,-95,-11,-93,-82,-47,125,-88,115,37,44,25,-77,-36,80,-60,-29,10,70,12,-70,-94,68,-116,-122,-71,-125,13,100,17,119,-70,69,-57,-31,123,68,95,-83,-78,-97,-119,-30,92,72,-100,35,124,102,-51,-72,-61,-98,-6,-13,83,50,84,-116,-89,62,-5,67,-48,-45,57,-3,-63,54,-50,-107,-20,-97,121,8,-118,12,-118,-22,81,-104,74,-9,108,74,31,-95,81,-98,-104,19,86,-70,118,50,104,100,-21,25,55,64,-39,53,69,52,-22,-42,-26,60,59,92,88,116,-49,80,32,25,58,66,79,76,-5,91,4,108,92,60,-42,-62,100,-100,8,-30,-96,-109,-34,-107,-37,61,10,55,-117,-45,-74,41,11,-30,23,79,78,13,-10,104,78,-97,-59,-16,-75,61,-87,8,-48,-1,78,5,63,56,-22,-32,-22,95,-66,-90,-25,-51,25,36,-122,32,-106,-61,50,-3,-14,-90,24,-118,95,7,66,118,-93,-20,-41,80,-36,-35,-28,-79,-107,45,47,17,-77,-99,-34,108,-77,-127,-6,82,28,-110,-74,82,43,-86,-87,-67,-92,96,-35,10,67,-56,-25,-80,90,-24,-21,15,7,42,-29,96,119,-1,115,-10,-53,24,-104,19,-15,75,-51,13,1,-11,103,76,-97,48,2,-126,-38,-107,22,-43,-70,29,-60,104,105,71,-28,9,121,-24,70,85,-30,110,-96,-44,-10,99,-12,119,-89,-119,-96,65,-117,-54,7,74,57,105,77,-119,-14,-75,18,84,9,-52,96,-1,-52,3,59,-105,92,67,49,88,-46,-50,-64,-102,-121,-79,52,24,-95,-47,-22,49,-28,36,8,41,11,20,-90,112,-127,92,38,-24,123,-23,44,111,-56,-113,-122,54,97,17,-87,-58,-76,-100,-34,65,-80,-56,45,-19,-35,109,101,38,-128,-75,28,-34,-10,-51,-30,-95,-105,-1,51,-78,-85,31,119,91,-128,55,-83,14,113,-53,105,-94,-119,10,-47,-107,-1,31,25,-121,-19,62,62,-61,101,83,81,-74,86,126,23,-36,-28,33,113,-123,34,109,115,-55,-63,115,40,18,58,117,120,37,-31,-60,58,102,14,-80,15,-56,-3,87,-66,-22,118,-124,-25,-119,-101,85,-58,-55,-74,51,-51,17,20,85,35,-33,20,82,-108,-3,81,-49,-41,111,11,-119,-105,-102,-114,-104,-124,-75,111,101,30,-4,-64,-76,-87,13,-33,109,94,71,-15,-85,-3,73,-114,68,-54,98,97,-99,-89,100,25,43,51,107,4,78,38,-63,116,-101,73,-101,32,-66,-38,-114,118,-69,76,-115,-61,55,75,5,-26,73,-22,-4,-10,-126,61,-101,103,126,-7,110,-12,122,109,109,72,7,-3,112,-125,-63,-100,92,-24,-33,114,74,-86,-124,34,-70,-95,19,115,86,-5,94,-101,126,94,71,-128,94,57,-82,-114,74,-34,103,-66,-86,81,-52,66,-39,-43,44,-111,-86,94,-21,9,-121,-41,70,8,98,16,125,21,10,46,85,-60,-8,-97,-52,-95,-96,-51,51,-86,26,113,35,75,62,13,43,89,28,40,-7,-7,124,72,106,68,-14,-39,-59,14,81,-84,51,-127,124,-93,-77,-28,-22,-109,62,-50,-73,119,90,-65,23,-48,-5,-1,-46,-32,49,20,28,75,-115,-1,64,-42,127,50,-8,-125,-54,-96,48,58,-57,35,85,-89,-45,-103,26,27,124,80,-63,105,44,56,68,10,94,-39,-31,-123,-15,122,103,-58,34,119,-71,-78,67,-75,-35,-79,-68,84,70,89,49,78,2,30,-37,112,-13,21,-10,16,-83,-15,-42,30,-72,121,65,-31,-25,-90,-1,99,-30,87,-45,-96,80,-100,52,-24,-47,-120,-115,13,-46,80,113,127,50,55,36,62,-116,-28,-126,-66,-127,-36,29,50,37,56,-65,-55,-43,-46,58,-88,-38,-53,-11,-106,66,37,14,2,14,34,-18,-14,91,-35,26,-71,37,117,115,1,-112,-61,11,-1,-24,22,-7,91,-122,49,33,46,-47,95,-37,119,-104,-128,95,25,-30,-66,-42,84,29,-100,-73,-128,-2,-50,87,17,47,-98,13,87,-34,100,101,96,103,-17,-102,62,13,-31,-57,-5,5,114,-46,-4,-101,-61,89,-57,124,-79,6,123,98,27,53,81,26,58,113,-35,21,1,107,-10,-32,1,123,-29,107,110,47,5,112,72,44,65,43,124,-72,-62,80,124,6,33,-31,-11,22,90,1,60,-101,79,-126,80,12,80,-40,58,71,-13,78,-7,10,-79,-13,-116,-110,68,-87,-76,103,-79,-60,66,-13,-87,-50,-16,-87,124,9,-50,-34,-84,125,-106,116,-109,46,-13,-23,-83,25,-110,70,-113,92,-107,60,-44,-49,25,74,20,-41,72,-43,-32,-64,41,-14,20,75,-106,-93,-54,67,-35,-89,2,58,11,123,3,-74,-60,-4,-8,-80,-26,-44,126,122,41,-90,-30,-34,54,75,-84,17,-18,-113,-79,84,-29,107,94,115,110,23,117,-21,56,-70,-46,18,5,17,-21,-92,103,-76,107,46,-77,-71,-21,-63,-48,-54,-107,98,111,68,12,-92,76,-128,110,-110,-37,28,37,67,-90,20,-58,-98,43,-5,75,35,99,97,-86,-112,-24,12,26,-72,-8,-47,74,21,-120,-88,21,47,93,76,-87,-121,-106,-99,81,-42,-33,79,-73,-48,1,-42,-53,-53,90,-4,48,-23,-3,-26,-102,87,13,-62,39,-46,-113,71,-30,-33,13,-74,90,31,26,89,58,79,19,52,-85,-96,-34,2,-98,111,-87,-124,43,-86,90,-122,22,-128,-120,95,89,3,64,-112,27,15,16,-6,-107,-118,-94,-93,-117,-20,-99,-49,-121,11,83,-12,35,-106,-11,30,-47,-30,89,-2,87,-86,-7,47,7,87,-88,77,-77,17,-24,-42,-79,-36,117,-113,100,-3,-22,45,-95,-108,-26,-15,64,-6,115,-57,-81,-54,-102,-36,-3,-94,75,48,70,66,51,-102,-4,-86,-41,16,100,-115,-68,-58,-64,104,-94,-101,25,-66,-29,-26,118,11,-6,25,66,58,-31,-9,14,6,91,-99,72,124,-70,126,-7,-111,-104,-104,-96,-6,-23,-105,53,40,39,-46,-37,-109,52,-114,28,106,-32,25,-82,49,-122,10,-103,-53,-36,-56,92,126,-85,-81,-96,-13,-26,-34,-33,-31,-76,-10,-97,-54,101,-92,-90,32,79,-115,-11,110,-75,35,-65,80,120,-6,-48,-73,-108,52,-46,8,60,26,27,-14,-37,30,-105,-23,-97,115,-105,-18,-14,6,59,-10,49,80,53,-94,-65,114,-120,95,-52,-16,-122,-25,-37,109,-55,-98,-82,51,125,33,24,-87,124,24,18,108,56,-52,121,-115,62,-85,112,-72,125,69,80,-28,-96,-56,-35,-94,34,75,119,99,72,6,-89,60,76,10,-66,121,-87,91,-1,-12,29,-84,122,-73,-51,87,80,-15,-70,-56,-27,-30,-109,23,-1,110,-58,37,-66,47,-86,-53,-114,-22,51,-88,60,-93,-56,1,33,66,50,109,89,-90,10,24,-12,-6,-43,84,30,-93,-10,-128,-70,46,-86,-114,16,-111,-90,-67,9,41,-83,-19,-23,87,76,64,-66,-117,82,-1,-69,22,46,-114,-103,-66,23,66,122,28,-116,89,-94,-32,56,-15,-99,-118,-70,111,122,-27,-6,61,109,-100,15,-81,4,-75,-95,-23,56,-73,-63,95,-27,-67,-66,-96,-18,103,5,-101,-79,-117,-2,-114,123,-25,-80,-72,-106,22,-50,-15,-7,-20,-30,-54,-16,52,-53,-60,85,54,-76,-76,63,-81,-71,-77,71,110,55,101,60,-2,-3,-8,10,-102,102,51,12,114,22,-42,89,95,65,114,-58,3,102,-67,-43,-95,-24,-70,64,-103,-79,111,91,16,18,121,112,74,-104,-104,-80,49,-84,-66,52,-126,1,-85,-57,11,-45,106,25,-113,29,23,88,-15,-69,35,45,63,-114,1,12,51,64,-67,101,34,-54,39,-38,-32,82,-1,-38,13,-18,-90,-103,-58,112,-37,101,-10,68,-89,70,-42,-68,-94,-126,-16,103,70,26,89,27,-68,-66,117,37,56,85,-51,8,-38,14,4,38,-16,-69,116,20,-48,56,18,-1,86,20,43,123,114,-108,13,106,81,-56,-76,-120,35,-39,-14,-105,-84,-11,-92,-91,-4,9,94,-123,36,-5,-124,-128,-69,-24,7,52,-93,-53,-101,-27,-48,55,-45,-127,-23,75,-37,104,68,-114,77,-63,59,-116,-80,-19,84,36,-87,-48,-44,30,88,112,-17,-35,107,35,30,-19,97,37,124,-128,63,-43,8,17,-124,126,-106,-125,109,86,-14,44,-33,-98,-108,-40,42,98,4,-44,-95,-46,35,107,52,-128,-18,-99,45,73,61,-115,33,-26,93,-52,-3,19,50,11,-26,-48,-81,-59,-125,-48,-76,-40,-48,-116,-83,103,12,-26,-11,53,-1,-87,76,38,-106,89,90,100,-90,-11,-53,93,67,50,88,-108,89,116,-89,4,-65,75,112,50,95,40,60,81,-87,15,101,47,107,-59,6,-64,-85,-94,33,-32,-12,-76,-24,-126,-38,-17,-24,-85,-86,-123,-31,-114,-49,-121,-59,-125,-114,-58,16,-115,-8,-23,86,28,-119,43,-3,-76,-124,23,99,-85,-86,-113,127,89,-26,56,-22,-116,28,106,-71,-122,-62,98,120,63,12,117,-1,-58,-112,90,16,-82,-92,-26,-89,-51,-33,-75,-10,77,76,-27,14,-78,23,117,7,65,-59,70,94,98,62,-124,-113,1,-113,-124,-47,84,-78,115,-63,42,-24,-72,-115,15,54,-71,50,73,-6,86,73,50,101,-101,13,81,-13,-94,83,18,98,-107,-43,-13,112,-63,-60,44,79,-122,-9,-91,-100,119,-50,-53,-43,61,117,68,-3,-26,-107,71,-75,-106,110,5,105,-126,-14,119,-14,29,90,66,-126,-74,6,-92,80,16,39,-55,8,92,-101,-3,-32,-83,-97,73,-5,7,-115,-23,-29,89,-63,54,-113,102,-28,43,80,-28,-15,-97,5,87,62,-111,40,34,42,44,48,73,-71,-8,72,-13,-106,17,-24,-31,-127,44,-50,-92,64,-82,1,-88,-86,-108,-17,34,-79,-38,8,101,4,-49,-15,109,-69,61,-11,-21,2,104,33,-13,-60,96,2,3,-14,-93,77,71,73,22,23,-92,24,77,77,-61,117,-128,-117,-99,101,34,-80,113,-6,-69,95,9,-28,-101,-28,71,-108,115,112,107,24,-107,77,-94,-34,122,70,76,-44,-38,-104,19,92,-109,37,-80,96,-23,117,21,-21,-68,9,69,-32,40,-125,-119,-8,122,77,98,92,-7,99,66,36,90,-116,-65,-37,-76,-113,25,73,78,32,76,107,-59,122,-91,-8,70,-11,39,12,-42,88,-77,-26,40,58,-53,-34,80,-104,-78,-25,68,49,-72,-3,-103,66,-70,100,-7,50,25,-18,21,109,71,107,121,76,54,-110,-37,96,97,-42,76,-62,-61,-16,68,66,17,21,15,-108,-62,92,55,-65,107,-105,-50,85,70,-58,66,-120,-23,-11,68,-50,112,47,113,67,-81,70,-128,41,40,-104,26,42,-124,63,-112,-39,99,-15,30,-40,10,17,-18,92,117,-60,-42,57,49,42,-76,-23,-76,-75,-84,22,125,54,-29,112,63,18,-11,84,100,27,-12,-93,-111,7,14,-123,-68,-57,98,77,46,44,106,95,30,2,-60,-55,12,-119,-80,-20,-66,-83,-24,-89,54,76,-24,38,-35,4,-101,51,30,-66,-125,-107,119,-18,54,-98,-79,55,93,103,-17,90,-52,-45,38,37,42,-119,69,50,51,-38,67,105,-37,-60,-59,120,-118,-109,6,-119,-89,-81,-16,-126,-104,-41,-12,-111,98,-34,81,113,120,90,-38,-105,32,-40,31,83,-127,44,65,63,59,-32,-82,-103,109,-77,-30,-80,127,-43,39,-61,13,66,-47,-37,-39,51,18,107,79,-121,-43,-6,19,-39,78,33,-86,-6,80,-39,124,86,122,-83,37,-111,17,-49,119,-22,-61,-77,-71,-105,-16,42,-6,-66,76,-46,-52,-68,78,23,103,41,-47,-78,-87,8,-92,-11,111,78,85,-98,-22,-45,-99,85,55,-13,-67,-103,-80,-23,-49,-122,-52,78,-103,16,9,-13,-39,-92,39,85,4,-98,-67,24,-53,-122,97,-67,-104,123,-5,122,65,6,-56,9,-101,-128,92,43,69,108,-10,-50,-55,-11,-15,-48,80,5,19,-35,87,-125,-111,120,21,51,14,126,-25,-11,-34,-127,97,122,12,64,-103,-72,-15,21,80,77,84,-53,-101,55,19,-124,18,-84,-7,118,10,-126,123,87,94,-42,100,99,-36,-29,51,-100,84,-100,-122,-22,-64,84,-13,85,-93,13,100,-36,43,-19,-18,-104,-66,-98,108,-43,-63,-65,-56,-9,82,-1,24,-15,-67,-66,17,107,-54,43,110,13,-63,-85,-57,52,63,-124,127,70,17,20,-26,85,-12,-11,-69,63,54,-28,25,13,-84,111,5,-92,-8,38,-7,-51,-78,60,23,-26,-38,-40,92,48,-85,-51,-25,85,-57,11,-79,90,-114,96,69,-69,-15,-98,-49,-39,12,-99,94,-12,-109,91,84,49,-105,-74,-88,-54,-27,58,-35,50,-40,-63,-101,65,113,22,104,-122,72,-98,18,-96,116,31,-121,-35,58,52,-75,123,113,-60,-121,-4,-15,-44,34,-38,-95,-12,115,92,121,124,10,-97,20,-34,27,102,-68,7,-56,-78,-93,-101,-90,38,41,-109,-56,-24,-24,108,-110,-127,-78,-89,-75,54,37,110,33,-111,-83,-39,-123,-80,29,-18,-3,-83,38,3,-29,29,13,-53,-7,-104,-30,76,41,-85,62,-128,62,16,-58,-110,103,-60,44,-46,-97,60,40,-11,-112,24,96,56,25,127,118,-112,72,-28,34,109,94,-69,-74,-91,-85,-128,-122,-46,-31,14,49,-52,11,-90,126,-112,87,-2,-93,-31,-7,-69,-51,-113,-102,123,-67,-64,35,-89,2,-63,-128,-34,44,26,-86,78,-66,89,-82,58,-3,-31,-120,-14,89,-38,99,47,78,33,-60,-36,-52,43,-87,-21,53,56,20,-39,-61,13,-88,-82,41,-39,23,-15,80,-81,-110,113,4,50,111,92,36,41,-89,116,6,45,-29,-73,109,40,-49,116,-70,-77,-84,-72,-114,27,-109,-126,99,68,99,-23,-69,-111,-86,-89,105,112,-33,-59,35,-74,-17,-50,-63,-13,-60,47,-65,6,-14,37,-71,125,65,-84,-17,-74,100,-7,-71,115,40,23,-58,99,63,-22,99,37,-63,-6,22,2,44,-37,28,83,-84,77,41,42,-37,-39,97,-7,6,-39,-13,48,-96,-71,69,-81,-77,-28,-112,-86,88,-110,-39,43,73,11,-18,78,-81,52,45,-94,-37,-93,3,92,80,50,14,28,-128,15,-99,-33,53,99,-3,-8,83,-111,-48,-113,-36,8,44,50,-48,-43,-72,36,-122,43,-18,40,-113,-38,-108,72,-68,-83,-89,-17,60,103,36,-100,-97,6,-99,-128,125,-125,-93,99,64,-66,126,63,-7,-26,24,-52,-38,64,57,-94,-57,-15,71,-3,102,-103,30,16,-23,35,84,66,13,-77,21,46,-68,-52,-45,21,-55,55,-74,39,40,99,64,-41,105,27,23,-64,-28,1,43,112,62,-30,-38,49,-86,-67,-6,53,-95,-19,42,28,118,73,-55,-66,91,84,102,24,72,84,102,-103,6,-26,89,-88,15,-123,-70,-6,46,6,110,-104,-50,38,26,-40,-68,35,12,26,126,6,22,22,-34,42,-23,17,-30,52,72,-90,47,74,-34,113,117,115,-109,-42,11,-88,-77,-98,-53,-88,-1,22,93,106,-88,40,118,-42,81,-5,38,36,-30,101,-125,57,-1,92,-77,120,-124,-87,81,-68,-27,-92,7,-120,-4,48,-116,-14,-31,-38,-122,28,51,-60,2,65,-19,-15,126,50,-125,61,116,101,-52,89,100,-14,-18,-48,-56,-27,103,-23,-68,-30,-43,81,15,50,-4,-106,-61,42,-48,-119,-55,53,-65,-99,-80,72,4,18,-66,15,37,1,-34,2,89,-37,-110,-35,27,-115,-58,120,125,-106,86,83,94,-64,-83,45,-89,101,75,45,84,73,-87,114,-33,55,-126,-110,95,-7,88,31,99,-79,117,68,108,91,-24,-64,24,-88,-92,-66,-45,81,-115,-115,-84,100,127,-95,112,100,-87,49,-114,77,-96,30,-35,-81,-113,-101,-121,-79,-113,-66,8,-124,-57,6,84,-58,11,48,83,68,4,81,72,15,73,8,-52,-1,4,-2,-74,-52,-84,-19,114,-49,-78,102,6,43,114,18,111,28,51,120,36,64,76,-126,99,29,-53,100,-94,30,-70,-71,-60,80,116,16,-12,-127,55,76,34,-49,109,-62,122,-30,11,33,62,-100,-4,98,6,-3,2,-121,-62,95,125,110,-60,2,37,108,-80,51,-12,-44,-98,10,98,54,-28,100,-5,37,-6,-111,99,-15,71,95,96,37,55,-10,39,70,100,21,118,124,-15,92,-1,47,120,-92,54,-87,85,92,-58,-128,106,11,74,-65,-113,98,34,-61,-49,37,-2,-110,24,-71,-36,62,-110,74,-68,36,-116,-124,-108,21,98,70,-93,-56,67,62,-83,-57,61,-127,48,68,36,120,-18,-74,-94,-110,97,-48,33,-25,-118,-125,-117,-111,-1,-28,-93,3,-120,-26,69,-33,-25,-121,-115,-53,-108,-98,-116,75,-78,5,102,103,-107,35,84,-80,26,-25,127,-74,82,-53,-121,125,-92,-43,-54,25,40,-99,-19,-39,24,66,-91,-12,6,-70,85,47,84,119,17,-74,6,-43,-97,105,102,-108,-50,111,-55,-46,104,42,-31,1,37,56,-112,-125,13,-61,97,-103,-73,-4,-65,13,84,-71,-128,-100,14,100,30,-6,-34,14,103,20,32,-15,7,-90,-74,-77,-23,-10,-70,-122,-101,36,19,-50,69,-19,-49,-6,36,25,57,-72,-4,13,52,96,63,34,94,-45,-20,-21,-32,44,9,-82,-68,-71,44,92,-7,95,-89,120,5,81,112,40,-48,90,10,-36,78,-66,107,-119,36,16,-66,99,-54,80,-50,-14,2,-89,88,-101,-66,-29,-97,-90,-17,-47,37,-8,-45,70,21,72,-39,92,-54,45,-112,-93,-28,60,-88,-53,26,-11,3,1,-47,32,65,-5,16,-120,69,88,28,57,80,31,-48,126,-67,-54,-31,62,76,-108,-85,112,-93,2,-106,64,23,95,12,-61,99,-57,33,-33,59,86,42,-21,118,70,-53,-125,108,63,-55,35,-21,-95,124,21,29,104,-105,-37,-24});
	}
//	public CipherRichard(byte[] _maskCrypto) {
//		init(_maskCrypto);
//	}
	private void init(byte[] _maskCrypto) {
		maskCrypto=_maskCrypto;
		maskLength=(short) maskCrypto.length;
		random=new Random();
	}
	
	public short getPublicKey() {
		return (short) random.nextInt(maskLength);
	}
	
	public byte[] insert2ByteEndCode(byte[] arryData,int arrayLength) {
		short publicKey = (short) random.nextInt(maskLength);
		short count=publicKey;
		
		byte[] result = new byte[arrayLength+2];
		result[0] = (byte) (publicKey>>>8);
		result[1] = (byte) publicKey;
		
		for(int i=0;i<arrayLength;i++) {
			result[2+arrayLength-i-1]=(byte) (arryData[i]^maskCrypto[count++]);
			if(count==maskLength)
				count=0;
		}
		return result;
	}
	
	public byte[] remove2ByteDecode(byte[] arryData,int arrayLength) {
		int ch1 = arryData[0] & 0xFF;
		int ch2 = arryData[1] & 0xFF;
		short publicKey = (short)((ch1 << 8) + (ch2 << 0));
		arrayLength-=2;
		
		byte[] result = new byte[arrayLength];
		short count=publicKey;
		for(int i=arrayLength-1;i>-1;i--) {
			result[arrayLength-i-1]=(byte) (arryData[i+2]^maskCrypto[count++]);
			if(count==maskLength)
				count=0;
		}
		return result;
	}
//	public byte[] endCode(short publicKey,byte[] arryData,int arrayLength) {
//		byte[] result = new byte[arrayLength];
//		short count=publicKey;
//		for(int i=0;i<arrayLength;i++) {
//			result[arrayLength-i-1]=(byte) (arryData[i]^maskCrypto[count++]);
//			if(count==maskLength)
//				count=0;
//		}
//		return result;
//	}
//	public byte[] deCode(short publicKey,byte[] arryData,int arrayLength) {
//		byte[] result = new byte[arrayLength];
//		short count=publicKey;
//		for(int i=arrayLength-1;i>-1;i--) {
//			result[arrayLength-i-1]=(byte) (arryData[i]^maskCrypto[count++]);
//			if(count==maskLength)
//				count=0;
//		}
//		return result;
//	}
	
	
	public byte[] getTokenData(long v) {
		byte[] temp=new byte[16];
		temp[0]   = (byte)(v >>> 56);
		temp[1] = (byte)(v >>> 48);
		temp[2] = (byte)(v >>> 40);
		temp[3] = (byte)(v >>> 32);
		temp[4] = (byte)(v >>> 24);
		temp[5] = (byte)(v >>> 16);
		temp[6] = (byte)(v >>>  8);
		temp[7] = (byte)(v >>>  0);
		v=System.currentTimeMillis();
		temp[8]   = (byte)(v >>> 56);
		temp[9] = (byte)(v >>> 48);
		temp[10] = (byte)(v >>> 40);
		temp[11] = (byte)(v >>> 32);
		temp[12] = (byte)(v >>> 24);
		temp[13] = (byte)(v >>> 16);
		temp[14] = (byte)(v >>>  8);
		temp[15] = (byte)(v >>>  0);
		return insert2ByteEndCode(temp, 16);
	}
}
