package fuse;

/**
 * User: peter
 * Date: Nov 11, 2005
 * Time: 10:15:48 AM
 */
public interface FuseStatConstants extends FuseFtypeConstants {
    // additional mode bits

    int MODE_MASK = 0007777;   // mode bits mask
    int SUID_BIT = 0004000;   // set UID bit
    int SGID_BIT = 0002000;   // set GID bit
    int STICKY_BIT = 0001000;   // sticky bit
    int OWNER_MASK = 0000700;   // mask for file owner permissions
    int OWNER_READ = 0000400;   // owner has read permission
    int OWNER_WRITE = 0000200;   // owner has write permission
    int OWNER_EXECUTE = 0000100;   // owner has execute permission
    int GROUP_MASK = 0000070;   // mask for group permissions
    int GROUP_READ = 0000040;   // group has read permission
    int GROUP_WRITE = 0000020;   // group has write permission
    int GROUP_EXECUTE = 0000010;   // group has execute permission
    int OTHER_MASK = 0000007;   // mask for permissions for others
    int OTHER_READ = 0000004;   // others have read permission
    int OTHER_WRITE = 0000002;   // others have write permisson
    int OTHER_EXECUTE = 0000001;   // others have execute permission
}
