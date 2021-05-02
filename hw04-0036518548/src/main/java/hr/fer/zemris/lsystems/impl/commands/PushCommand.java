package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
/**
 * Metoda koja predstavlja komandu push.
 * @author Andrea
 *
 */
public class PushCommand implements Command{
	
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState copy = ctx.getCurrentState().copy();
		ctx.pushState(copy);
	}

}
