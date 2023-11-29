local value = tonumber(redis.call('GET', KEYS[1]))
if (value > 0)
then
    redis.call('SET', KEYS[1], tostring(value - 1))
    return tostring(value)
else
    return tostring(value)
end