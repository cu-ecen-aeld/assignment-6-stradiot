inherit module

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-stradiot.git;protocol=ssh;branch=main \
		file://aesdchar-start-stop \
		"

PV = "1.0+git${SRCPV}"
SRCREV = "cb537541964fa8727436636d33ba5a4cdf8dfc8f"

S = "${WORKDIR}/git/aesd-char-driver"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdchar-start-stop"

FILES:${PN} += "${bindir}/aesdchar_load"
FILES:${PN} += "${bindir}/aesdchar_unload"
FILES:${PN} += "${INIT_D_DIR}/aesdchar-start-stop"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	install -d ${D}${INIT_D_DIR}
	install -d ${D}${bindir}
	install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel

	install -m 0755 ${S}/aesdchar.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/aesdchar.ko
	install -m 0755 ${S}/../../aesdchar-start-stop ${D}${INIT_D_DIR}
	install -m 0755 ${S}/aesdchar_load ${D}${bindir}/aesdchar_load
	install -m 0755 ${S}/aesdchar_unload ${D}${bindir}/aesdchar_unload
}
