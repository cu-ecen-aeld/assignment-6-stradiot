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

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-stradiot.git;protocol=ssh;branch=main \
   		   file://0001-limit-targets.patch \
   		   file://module-start-stop \
   		   file://module_load \
   		   file://module_unload \
   		   file://unset_faulty.cfg \
           "

PV = "1.0+git${SRCPV}"
SRCREV = "d0ea7c1e3da1dcd84203d7cba372b87496e82864"

S = "${WORKDIR}/git"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "module-start-stop"

FILES:${PN} += "${bindir}/module_load"
FILES:${PN} += "${bindir}/module_unload"
FILES:${PN} += "${INIT_D_DIR}/module-start-stop"

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
	install -m 0755 ${S}/misc-modules/faulty.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/faulty.ko
	install -m 0755 ${S}/misc-modules/hello.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/hello.ko
	install -m 0755 ${S}/../module-start-stop ${D}${INIT_D_DIR}
	install -m 0755 ${S}/../module_load ${D}${bindir}/module_load
	install -m 0755 ${S}/../module_unload ${D}${bindir}/module_unload
}
