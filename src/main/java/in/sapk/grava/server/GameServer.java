package in.sapk.grava.server;

import java.util.*;
import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created by george on 27/05/15.
 */
public class GameServer {

    private static final Logger LOGGER = Logger.getLogger(GameServer.class.getName());

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
     * @throws NullPointerException
     */
    public void join(GameTransport transport) {
        checkNotNull(transport, "transport cannot be null");

        LOGGER.info(transport.getId() + ": joining game");

        GameSession session = getLastSession();
        session.join(transport);

        LOGGER.info(transport.getId() + ": joined game " + session.getIdA());

        sessionMap.put(transport.getId(), session);
    }

    public void sow(final String sessionId, final int idx) {
        checkArgument(!isNullOrEmpty(sessionId), "sessionId cannot be null or empty");
        checkState(sessionMap.containsKey(sessionId),
                "Game session " + sessionId + " doesn't exist");

        GameSession session = sessionMap.get(sessionId);
        session.sow(sessionId, idx);
    }

    public void leave(final String sessionId) {
        checkArgument(!isNullOrEmpty(sessionId), "sessionId cannot be null or empty");

        if (!sessionMap.containsKey(sessionId)) {
            return;
        }

        LOGGER.info(sessionId + ": leaving");

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
        }

        if (session == null || session.isFull()) {
            session = new GameSession();

            sessions.add(session);
        }

        return session;
    }
}
