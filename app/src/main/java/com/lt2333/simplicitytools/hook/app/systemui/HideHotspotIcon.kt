package com.lt2333.simplicitytools.hook.app.systemui

import com.lt2333.simplicitytools.util.XSPUtils
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class HideHotspotIcon :IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        val classIfExists = XposedHelpers.findClassIfExists(
            "com.android.systemui.statusbar.phone.PhoneStatusBarPolicy\$2",
            lpparam.classLoader
        )
        XposedHelpers.findAndHookMethod(
            classIfExists,
            "onHotspotChanged",
            Boolean::class.java, Int::class.java,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    if (XSPUtils.getBoolean("hide_hotspot_icon", false)) {
                        param.result = null
                    }
                }
            })
    }
}