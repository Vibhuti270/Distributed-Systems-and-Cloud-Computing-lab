package rpc;

import java.io.Serializable;

// This is the interface that defines the remote method.
public interface RPCInterface extends Serializable {
// Remote procedure to add two numbers.
    int add(int a, int b);
}
