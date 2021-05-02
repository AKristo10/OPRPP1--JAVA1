package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;
/**
 * Sucelje koje predstavlja naredbu.
 * @author Andrea
 *
 */
public interface Command {
	void execute(Context ctx, Painter painter);
}
