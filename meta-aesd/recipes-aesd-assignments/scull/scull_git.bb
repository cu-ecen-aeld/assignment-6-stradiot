# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
inherit module

LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-stradiot.git;protocol=ssh;branch=main \
   		   file://0001-limit-targets.patch \
   		   file://scull-start-stop \
   		   file://scull_load \
   		   file://scull_unload \
           "

PV = "1.0+git${SRCPV}"
SRCREV = "d0ea7c1e3da1dcd84203d7cba372b87496e82864"

S = "${WORKDIR}/git"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "scull-start-stop"

FILES:${PN} += "${bindir}/scull_load"
FILES:${PN} += "${bindir}/scull_unload"
FILES:${PN} += "${INIT_D_DIR}/scull-start-stop"

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
	install -m 0755 ${S}/scull/scull.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/scull.ko
	install -m 0755 ${S}/../scull-start-stop ${D}${INIT_D_DIR}
	install -m 0755 ${S}/../scull_load ${D}${bindir}/scull_load
	install -m 0755 ${S}/../scull_unload ${D}${bindir}/scull_unload
}
