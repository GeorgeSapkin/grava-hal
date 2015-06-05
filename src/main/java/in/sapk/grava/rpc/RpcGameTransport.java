package in.sapk.grava.rpc;

import in.sapk.grava.game.Board;
import in.sapk.grava.game.Side;
import in.sapk.grava.game.Turn;
import in.sapk.grava.rpc.protocol.RpcProtocol;
import in.sapk.grava.server.GameTransport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * GameTransport implementation to abstract RPC session and protocol from
 * GameServer.
 *
 * @author George Sapkin
 * @version 1.0
 * @since 2015-06-04
 */
class RpcGameTransport implements GameTransport {

    private static final Logger LOGGER = Logger.getLogger(RpcGameTransport.class.getName());

    private static final String LOGIN_METHOD = "login";
    private static final String TURN_METHOD = "turn";
    private static final String UPDATE_METHOD = "update";
    private static final String SESSION_KEY = "session";
    private static final String SIDE_KEY = "side";
    private static final String TYPE_KEY = "turnType";

    private final RpcSession session;
    private final RpcProtocol protocol;

    public RpcGameTransport(RpcSession session, RpcProtocol protocol) {
        checkNotNull(session, "session cannot be null");
        checkNotNull(protocol, "protocol cannot be null");

        this.session = session;
        this.protocol = protocol;
    }

    @Override
    public String getId() {
        return session.getId();
    }

    @Override
    public void notifyLogin(Side side) {
        Map<String, String> params = new HashMap<>();
        params.put(SESSION_KEY, session.getId());
        params.put(SIDE_KEY, side.name());
        String loginMsg = protocol.getNotification(LOGIN_METHOD, params);

        try {
            session.sendMessage(loginMsg);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, session.getId() + ": failed to send message", e);
        }
    }

    @Override
    public void notify(Turn turn, Board board) {
        checkNotNull(turn, "turn cannot be null");
        checkNotNull(board, "board cannot be null");

        List<String> updateParams = new ArrayList<>();
        board.forEach((p) -> updateParams.add(Integer.toString(p.getStones())));

        String updateMsg = protocol.getNotification(UPDATE_METHOD, updateParams);
        try {
            session.sendMessage(updateMsg);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, session.getId() + ": failed to send message", e);
        }

        Map<String, String> turnParams = new HashMap<>();
        turnParams.put(SIDE_KEY, turn.getSide().name());
        turnParams.put(TYPE_KEY, turn.getType().name());

        String turnMsg = protocol.getNotification(TURN_METHOD, turnParams);
        try {
            session.sendMessage(turnMsg);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, session.getId() + ": failed to send message", e);
        }
    }

    @Override
    public void close() {
        try {
            session.close();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, session.getId() + ": failed to close", e);
        }
    }
}
