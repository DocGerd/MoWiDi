/**
 *   FUSE-J: Java bindings for FUSE (Filesystem in Userspace by Miklos Szeredi (mszeredi@inf.bme.hu))
 *
 *   Copyright (C) 2003 Peter Levart (peter@select-tech.si)
 *
 *   This program can be distributed under the terms of the GNU LGPL.
 *   See the file COPYING.LIB
 */
package fuse;

/**
 * This is an enumeration of error return values.
 */
interface Errno {
    //
    // generated from <errno.h>

    /** Operation not permitted. */
    int EPERM = 1;
    /** No such file or directory. */
    int ENOENT = 2;
    /** No such process. */
    int ESRCH = 3;
    /** Interrupted system call. */
    int EINTR = 4;
    /** I/O error. */
    int EIO = 5;
    /** No such device or address. */
    int ENXIO = 6;
    /** Arg list too long. */
    int E2BIG = 7;
    /** Exec format error. */
    int ENOEXEC = 8;
    /** Bad file number. */
    int EBADF = 9;
    /** No child processes. */
    int ECHILD = 10;
    /** Try again. */
    int EAGAIN = 11;
    /** Out of memory. */
    int ENOMEM = 12;
    /** Permission denied. */
    int EACCES = 13;
    /** Bad address. */
    int EFAULT = 14;
    /** Block device required. */
    int ENOTBLK = 15;
    /** Device or resource busy. */
    int EBUSY = 16;
    /** File exists. */
    int EEXIST = 17;
    /** Cross-device link. */
    int EXDEV = 18;
    /** No such device. */
    int ENODEV = 19;
    /** Not a directory. */
    int ENOTDIR = 20;
    /** Is a directory. */
    int EISDIR = 21;
    /** Invalid argument. */
    int EINVAL = 22;
    /** File table overflow. */
    int ENFILE = 23;
    /** Too many open files. */
    int EMFILE = 24;
    /** Not a typewriter. */
    int ENOTTY = 25;
    /** Text file busy. */
    int ETXTBSY = 26;
    /** File too large. */
    int EFBIG = 27;
    /** No space left on device. */
    int ENOSPC = 28;
    /** Illegal seek. */
    int ESPIPE = 29;
    /** Read-only file system. */
    int EROFS = 30;
    /** Too many links. */
    int EMLINK = 31;
    /** Broken pipe. */
    int EPIPE = 32;
    /** Math argument out of domain of func. */
    int EDOM = 33;
    /** Math result not representable. */
    int ERANGE = 34;
    /** Resource deadlock would occur. */
    int EDEADLK = 35;
    /** File name too long. */
    int ENAMETOOLONG = 36;
    /** No record locks available. */
    int ENOLCK = 37;
    /** Function not implemented. */
    int ENOSYS = 38;
    /** Directory not empty. */
    int ENOTEMPTY = 39;
    /** Too many symbolic links encountered. */
    int ELOOP = 40;
    /** Operation would block. */
    int EWOULDBLOCK = EAGAIN;
    /** No message of desired type. */
    int ENOMSG = 42;
    /** Identifier removed. */
    int EIDRM = 43;
    /** Channel number out of range. */
    int ECHRNG = 44;
    /** Level 2 not synchronized. */
    int EL2NSYNC = 45;
    /** Level 3 halted. */
    int EL3HLT = 46;
    /** Level 3 reset. */
    int EL3RST = 47;
    /** Link number out of range. */
    int ELNRNG = 48;
    /** Protocol driver not attached. */
    int EUNATCH = 49;
    /** No CSI structure available. */
    int ENOCSI = 50;
    /** Level 2 halted. */
    int EL2HLT = 51;
    /** Invalid exchange. */
    int EBADE = 52;
    /** Invalid request descriptor. */
    int EBADR = 53;
    /** Exchange full. */
    int EXFULL = 54;
    /** No anode. */
    int ENOANO = 55;
    /** Invalid request code. */
    int EBADRQC = 56;
    /** Invalid slot. */
    int EBADSLT = 57;
    /** Resource deadlock would occur. */
    int EDEADLOCK = EDEADLK;
    /** Bad font file format. */
    int EBFONT = 59;
    /** Device not a stream. */
    int ENOSTR = 60;
    /** No data available. */
    int ENODATA = 61;
    /** Timer expired. */
    int ETIME = 62;
    /** Out of streams resources. */
    int ENOSR = 63;
    /** Machine is not on the network. */
    int ENONET = 64;
    /** Package not installed. */
    int ENOPKG = 65;
    /** Object is remote. */
    int EREMOTE = 66;
    /** Link has been severed. */
    int ENOLINK = 67;
    /** Advertise error. */
    int EADV = 68;
    /** Srmount error. */
    int ESRMNT = 69;
    /** Communication error on send. */
    int ECOMM = 70;
    /** Protocol error. */
    int EPROTO = 71;
    /** Multihop attempted. */
    int EMULTIHOP = 72;
    /** RFS specific error. */
    int EDOTDOT = 73;
    /** Not a data message. */
    int EBADMSG = 74;
    /** Value too large for defined data type. */
    int EOVERFLOW = 75;
    /** Name not unique on network. */
    int ENOTUNIQ = 76;
    /** File descriptor in bad state. */
    int EBADFD = 77;
    /** Remote address changed. */
    int EREMCHG = 78;
    /** Can not access a needed shared library. */
    int ELIBACC = 79;
    /** Accessing a corrupted shared library. */
    int ELIBBAD = 80;
    /** .lib section in a.out corrupted. */
    int ELIBSCN = 81;
    /** Attempting to link in too many shared libraries. */
    int ELIBMAX = 82;
    /** Cannot exec a shared library directly. */
    int ELIBEXEC = 83;
    /** Illegal byte sequence. */
    int EILSEQ = 84;
    /** Interrupted system call should be restarted. */
    int ERESTART = 85;
    /** Streams pipe error. */
    int ESTRPIPE = 86;
    /** Too many users. */
    int EUSERS = 87;
    /** Socket operation on non-socket. */
    int ENOTSOCK = 88;
    /** Destination address required. */
    int EDESTADDRREQ = 89;
    /** Message too long. */
    int EMSGSIZE = 90;
    /** Protocol wrong type for socket. */
    int EPROTOTYPE = 91;
    /** Protocol not available. */
    int ENOPROTOOPT = 92;
    /** Protocol not supported. */
    int EPROTONOSUPPORT = 93;
    /** Socket type not supported. */
    int ESOCKTNOSUPPORT = 94;
    /** Operation not supported on transport endpoint. */
    int EOPNOTSUPP = 95;
    /** Protocol family not supported. */
    int EPFNOSUPPORT = 96;
    /** Address family not supported by protocol. */
    int EAFNOSUPPORT = 97;
    /** Address already in use. */
    int EADDRINUSE = 98;
    /** Cannot assign requested address. */
    int EADDRNOTAVAIL = 99;
    /** Network is down. */
    int ENETDOWN = 100;
    /** Network is unreachable. */
    int ENETUNREACH = 101;
    /** Network dropped connection because of reset. */
    int ENETRESET = 102;
    /** Software caused connection abort. */
    int ECONNABORTED = 103;
    /** Connection reset by peer. */
    int ECONNRESET = 104;
    /** No buffer space available. */
    int ENOBUFS = 105;
    /** Transport endpoint is already connected. */
    int EISCONN = 106;
    /** Transport endpoint is not connected. */
    int ENOTCONN = 107;
    /** Cannot send after transport endpoint shutdown. */
    int ESHUTDOWN = 108;
    /** Too many references: cannot splice. */
    int ETOOMANYREFS = 109;
    /** Connection timed out. */
    int ETIMEDOUT = 110;
    /** Connection refused. */
    int ECONNREFUSED = 111;
    /** Host is down. */
    int EHOSTDOWN = 112;
    /** No route to host. */
    int EHOSTUNREACH = 113;
    /** Operation already in progress. */
    int EALREADY = 114;
    /** Operation now in progress. */
    int EINPROGRESS = 115;
    /** Stale NFS file handle. */
    int ESTALE = 116;
    /** Structure needs cleaning. */
    int EUCLEAN = 117;
    /** Not a XENIX named type file. */
    int ENOTNAM = 118;
    /** No XENIX semaphores available. */
    int ENAVAIL = 119;
    /** Is a named type file. */
    int EISNAM = 120;
    /** Remote I/O error. */
    int EREMOTEIO = 121;
    /** Quota exceeded. */
    int EDQUOT = 122;
    /** No medium found. */
    int ENOMEDIUM = 123;
    /** Wrong medium type. */
    int EMEDIUMTYPE = 124;
    // extended attributes support needs these...
    /** No such attribute. */
    int ENOATTR = ENODATA;
    /** Operation is not supported. */
    int ENOTSUPP = 524;
}
