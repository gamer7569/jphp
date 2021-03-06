<?php
namespace php\io;

use Iterator;

abstract class Stream
{
    /**
     * @var string
     */
    private $path;

    /**
     * @var string
     */
    private $mode;

    /**
     * @return string
     */
    public function getPath()
    {
        return $this->path;
    }

    /**
     * @return string
     */
    public function getMode()
    {
        return $this->mode;
    }

    /**
     * @param int $length - count of bytes
     * @throws IOException
     * @return mixed
     */
    abstract public function read($length);

    /**
     * @throws IOException
     * @return mixed
     */
    abstract public function readFully();

    /**
     * @param string $value
     * @param null|int $length
     * @throws IOException
     * @return int
     */
    abstract public function write($value, $length = null);

    /**
     * @return bool
     */
    abstract public function eof();

    /**
     * @param int $position
     * @throws IOException
     * @return mixed
     */
    abstract public function seek($position);

    /**
     * @throws IOException
     * @return int
     */
    abstract public function getPosition();

    /**
     * @return mixed
     */
    abstract public function close();

    /**
     * @param string $path
     * @param null|string $mode
     * @return Stream
     */
    public function __construct($path, $mode = null)
    {
    }

    /**
     * @param $context
     * @return void
     */
    public function setContext($context)
    {
    }

    /**
     * @return mixed
     */
    public function getContext()
    {
    }

    /**
     * Alias of readFully() with converting to string always.
     * @return string
     */
    public function __toString()
    {
    }


    /**
     * @param string $path
     * @param string $mode
     * @return Stream
     * @throws IOException
     */
    public static function of($path, $mode = 'r')
    {
    }

    /**
     * Create a stream, call and return the result of the readFully() method, finally call the close() method.
     * @param string $path
     * @param string $mode
     * @return string binary
     * @throws IOException
     */
    public static function getContents($path, $mode = 'r')
    {
    }

    /**
     * Create a stream, call and return the result of the write() method, finally call the close() method.
     * @param string $path
     * @param string $data
     * @param string $mode
     * @throws IOException
     */
    public static function putContents($path, $data, $mode = 'w+')
    {
    }

    /**
     * Open a stream and close it after calling $onAccess automatically.
     * @param string $path
     * @param callable $onAccess (Stream $stream)
     * @param string $mode
     * @throws IOException
     */
    public static function tryAccess($path, callable $onAccess, $mode = 'r')
    {
    }

    /**
     * Checks stream is exists. It tries to open a stream and if all is ok, returns true and closes it.
     * @param string $path
     * @throws \Exception if you check external streams like http or ftp
     * @return bool
     */
    public static function exists($path)
    {
    }

    /**
     * @param string $protocol
     * @param string $className
     */
    public static function register($protocol, $className)
    {
    }

    /**
     * @param $protocol
     */
    public static function unregister($protocol)
    {
    }
}
