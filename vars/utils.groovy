#!/usr/bin/groovy

def safe(str) {
    return safe(str, "_")
}

def safe(str,replace) {
    def pattern = "[/<>:?*|-]"
    if (replace.matches(pattern)) {
        return "Replace value is invalid!!!. Replace pattern to avoid: " + pattern  
    } else {
        return str.replaceAll(pattern, replace)
    }
}

def timestamp () {
    def now = new Date()
    return now.format("YYMMddHHmm")
}

def versionTimestamp () {
    def now = new Date()
    return now.format("YYDHHmm")
}

def versionYear () {
    def now = new Date()
    return now.format("YYYY")
}

def getVersion () {
    def now = new Date()
    return now.format("YYYY") + "." + now.format("DHHmm")
}
