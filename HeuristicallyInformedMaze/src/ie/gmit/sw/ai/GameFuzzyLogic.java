package ie.gmit.sw.ai;

/*import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class GameFuzzyLogic {

	public static void main(String[] args) {
		
		String fileName = "fcl/strength.fcl";
		FIS fis = FIS.load(fileName, true);
		
		if( fis == null ) { 
            System.err.println("Can't load file: '" + fileName + "'");
            return;
        }

		FunctionBlock functionBlock = fis.getFunctionBlock("strength");
		JFuzzyChart.get().chart(functionBlock);
		
		fis.setVariable("weapon", 1);
        fis.setVariable("playerStrength", 9);
        fis.setVariable("opponentStrength", 3);
        
        fis.evaluate();
        
        // Show output variable's chart
        Variable victory = functionBlock.getVariable("victory");
        System.out.println(victory.getDefaultValue());
        System.out.println("Value: " + victory.getValue());
        JFuzzyChart.get().chart(victory, victory.getDefuzzifier(), true);

        // Print ruleSet
        System.out.println(fis);

	}
	
	public boolean fight(Weapon weapon, Opponent opponent, Player player)
	{
		 
		String fileName = "fcl/strength.fcl";
		FIS fis = FIS.load(fileName, true);
		
		fis.setVariable("weapon", weapon.getPower());
		fis.setVariable("opponentStrength", opponent.getStrength());
		fis.setVariable("playerStrength", player.getLife());
		fis.evaluate();
		
		double victory = fis.getVariable("victory").getValue();
		
		//this.getLifeForce() = this.getLifeForce() * victory;
		 
		 return true;
	}
	
}
*/