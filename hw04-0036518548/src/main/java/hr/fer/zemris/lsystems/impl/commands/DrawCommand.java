package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;
/**
 * Metoda koja predstavlja komandu za crtanje
 * @author Andrea
 *
 */
public class DrawCommand implements Command {
	double step;
	/**
	 * Konstruktor koji prima duljinu stepa.
	 * @param step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D vector = state.getPosition().added(state.getDirection().scaled(step * state.getShift()));

		painter.drawLine(state.getPosition().getX(), state.getPosition().getY(), vector.getX(), vector.getY(), state.getColor(), 1f);
		state.setPosition(vector);
	}

}
