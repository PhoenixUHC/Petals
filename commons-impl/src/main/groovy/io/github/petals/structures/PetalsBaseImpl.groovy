package io.github.petals.structures

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import io.github.petals.api.structures.*

import redis.clients.jedis.JedisPooled

@CompileStatic
class PetalsBaseImpl implements PetalsBase {
    private String uniqueId
    protected JedisPooled pooled

    PetalsBaseImpl(String uniqueId, JedisPooled pooled) {
        this.uniqueId = uniqueId
        this.pooled = pooled
    }

    String uniqueId() {
        this.uniqueId
    }

    boolean exists() {
        this.pooled.exists(this.uniqueId)
    }

    void delete() {
        this.pooled.del(this.uniqueId)
    }

    def getProperty(String name) {
        if (name == "uniqueId") return this.uniqueId
        if (name == "pooled") return this.pooled

        String value = this.pooled.hget(this.uniqueId, name)
        if (value == null) return null
        if (value == "true") return true
        if (value == "false") return false

        if (value.isNumber()) {
            if (value.contains(".")) return value.toDouble()
            else return value.toLong()
        }
        return value
    }

    void setProperty(String property, Object newValue) {
        if (property == "uniqueId" || property == "pooled") return

        if (newValue == null)
            this.pooled.hdel(this.uniqueId, property)
        else
            this.pooled.hset(this.uniqueId, property, String.valueOf(newValue))
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    def asType(Class target) {
        def map = [:]

        target.methods.each { m ->
            if (m.name.startsWith("get") || m.name.startsWith("set")) {
                def name = "${m.name.charAt(3).toLowerCase()}${m.name.substring(4)}"
                if (m.name.startsWith("get")) map."$m.name" = {
                    def v = this.getProperty(name)
                    return v == null ? 0.asType(m.returnType) : v
                }
                else map."$m.name" = { Object[] args -> this.setProperty(name, args[0]) }
            } else map."$m.name" = { Object[] args -> this.invokeMethod(m.name, args) }
        }

        return map.asType(target)
    }
}

