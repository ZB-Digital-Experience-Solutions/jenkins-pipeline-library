#!/usr/bin/groovy

def safe(str) {
    return safe(str, "_")
}

def safe(str,replace) {
    def pattern = "[/<>:?*|]"
    if (replace.matches(pattern)) {
        return "Replace value is invalid!!!"  
    } else {
        return str.replaceAll(pattern, replace)
    }
}
