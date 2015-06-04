package in.sapk.grava.server;

import java.util.*;

/**
 * Created by george on 27/05/15.
 */
public class GameServer {

    private final List<GameSession> sessions;
    private final Map<String, GameSession> sessionMap;

    public GameServer() {
        sessions = Collections.synchronizedList(new ArrayList<>());
        sessionMap = Collections.synchronizedMap(new HashMap<>());
    }

    /**s
     * Joins new or existing game session
     *
     * @param transport GameTransport to be added to a GameSession
     */
    public void join(GameTransport transport) {
        System.out.println("gameServer.join " + transport.getId());

        GameSession session = getLastSession();
        session.join(transport);

        sessionMap.put(transport.getId(), session);
    }

    public void sow(final String sessionId, final int idx) {
        if (!sessionMap.containsKey(sessionId))
            throw new IllegalStateException(
                    "Game session " + sessionId + " doesn't exist");

        GameSession session = sessionMap.get(sessionId);
        session.sow(sessionId, idx);
    }

    public void leave(final String sessionId) {
        if (!sessionMap.containsKey(sessionId))
            return;

        System.out.println("Closing session " + sessionId);

        GameSession session = sessionMap.get(sessionId);
        session.close();

        sessionMap.remove(session.getIdA());
        sessionMap.remove(session.getIdB());
        sessions.remove(session);
    }

    private GameSession getLastSession() {
        GameSession session = null;

        if (!sessions.isEmpty()) {
            session = sessions.get(sessions.size() - 1);
            System.out.println("Found existing game session " + Thread.currentThread().getId());
        }

        if (session == null || session.isFull()) {
            System.out.println("Creating new game session " + Thread.currentThread().getId());
            session = new GameSession();

            sessions.add(session);
        } else
            System.out.println("Using existing game session" + Thread.currentThread().getId());

        return session;
    }
}
