import hkube.algo.wrapper.IAlgorithm;
import hkube.api.IHKubeAPI;
import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.*;

public class InputOutput implements IAlgorithm {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void Init(Map args) {
        if (logger.isDebugEnabled()) {
            logger.debug(new JSONObject((args)).toString());
        }
    }


    @Override
    public Map Start(Map args, IHKubeAPI hkubeAPI) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug(args.toString());
        }

        List outputList = new ArrayList();
        Collection input = (Collection)args.get("input");
        input.stream().forEach(item -> {
            if (item instanceof ByteBuffer) {
                byte[] arr = new byte[((ByteBuffer) item).remaining()];
                ((ByteBuffer) item).get(arr);
                outputList.add(arr);
            } else outputList.add(item);
        });
        Map output = new HashMap();
        output.put("prevInput", outputList);
        String index0 = null;
        try {
            if (input.size() > 0)
                index0 = input.iterator().next().toString();
        } catch (Exception e) {

        }
        if (input.size() > 1 && index0 != null && index0.equals("b")) {
            Integer numberOfbytes = Integer.valueOf(input.iterator().next().toString());
            byte[] myBytes = new byte[numberOfbytes];
            output.put("addedBytes", myBytes);
        }
        return output;

    }

    @Override
    public void Stop() {

    }

    @Override
    public void Cleanup() {

    }
}
