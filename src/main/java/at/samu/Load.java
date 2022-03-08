package at.samu;

import at.samu.packet.NickPacketListener;
import com.comphenix.protocol.ProtocolLibrary;

public class Load {

    public Load(Main main) {
        ProtocolLibrary.getProtocolManager().addPacketListener(new NickPacketListener(main));
    }

}
