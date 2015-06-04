package in.sapk.grava.rpc;

import in.sapk.grava.game.Pits;
import in.sapk.grava.game.Side;
import in.sapk.grava.game.Turn;
import in.sapk.grava.rpc.protocol.RpcProtocol;
import in.sapk.grava.server.GameTransport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by george on 03/06/15.
 */
class RpcGameTransport implements GameTransport {

    private static final String LOGIN_METHOD = "login";
    private static final String TURN_METHOD = "turn";
    private static final String UPDATE_METHOD = "update";
    private static final String SESSION_KEY = "session";
    private static final String SIDE_KEY = "side";
    private static final String TYPE_KEY = "turnType";

    private RpcSession session;
    private RpcProtocol protocol;

    public RpcGameTransport(RpcSession session, RpcProtocol protocol) {
        if (session == null) {
            throw new IllegalArgumentException("session cannot be null");
        }

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
            session.sendText(loginMsg);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void notify(Turn turn, Pits pits) {
        System.out.println("Notifying " + getId());

        List<String> updateParams = new ArrayList<>();
        pits.forEach((p) -> updateParams.add(Integer.toString(p.getStones())));

        String updateMsg = protocol.getNotification(UPDATE_METHOD, updateParams);
        try {
            session.sendText(updateMsg);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Map<String, String> turnParams = new HashMap<>();
        turnParams.put(SIDE_KEY, turn.getSide().name());
        turnParams.put(TYPE_KEY, turn.getType().name());

        String turnMsg = protocol.getNotification(TURN_METHOD, turnParams);
        try {
            session.sendText(turnMsg);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            session.close();
        } catch (IOException ex) {
            System.out.print("Failed closing session " + getId());
        }
    }
}
