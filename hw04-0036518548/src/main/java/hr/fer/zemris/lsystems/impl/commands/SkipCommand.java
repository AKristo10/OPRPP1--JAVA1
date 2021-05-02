package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;
/**
 * Klasa koja predstavlja komandu Skip
 * @author Andrea
 *
 */
public class SkipCommand implements Command {

	private double step;
	/**
	 * Konstruktor koji prima duljinu stepa.
	 * @param step
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();

		Vector2D vector = state.getPosition().added(state.getDirection().scaled(step * state.getShift()));
		state.setPosition(vector);
	}

}
