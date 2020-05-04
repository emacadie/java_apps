From Portland Ruby list:
The ruby-amqp project has all of the current ones:

https://github.com/ruby-amqp

If you're only publishing to AMQP, 'bunny' is generally fine, unless
you need the publishing to be asynchronous, or unless you're trying to
use transactions (in which case you need the publishing to be
asynchronous). This is what I use to publish from Rails web processes
in my AMQP-based job queue Woodhouse.

If you're developing a long-running process that needs to listen to
queues, you'll want to use either amqp or hot_bunnies. amqp is the
only choice for CRuby, but it uses EventMachine, which has some
caveats (it interacts poorly with threaded code). hot_bunnies is
JRuby-only, and it performs great. It's what I use for the backend
processes in that job queue.

There's also amq-client, which is low-level and might be useful to you
if neither amqp or hot_bunnies serve your needs.

Hope that helps!

