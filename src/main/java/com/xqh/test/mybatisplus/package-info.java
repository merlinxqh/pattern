/**
 * ========sql 语句 and or 写法
 * new QueryWrapper<HotelGuestInfo>().lambda()
 *                         .eq(HotelGuestInfo::getGuestType, GuestTypeEnums.CLUB_GUEST.toString())
 *                         .and(
 *                             wrapper -> wrapper.eq(HotelGuestInfo::getUpStatus, HotelServiceConstants.STATUS_DISABLED)
 *                                     .or()
 *                                     .eq(HotelGuestInfo::getPushStatus, HotelServiceConstants.STATUS_DISABLED)
 *                         )
 *                         .last(" limit 100")
 */
package com.xqh.test.mybatisplus;